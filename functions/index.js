const { onValueCreated } = require("firebase-functions/v2/database");
const admin = require("firebase-admin");
const { GoogleGenerativeAI } = require("@google/generative-ai");
require("dotenv").config();

admin.initializeApp();

const genAI = new GoogleGenerativeAI(process.env.GEMINI_API_KEY);

exports.onTriggerReceived = onValueCreated("/triggers/{triggerId}", async (event) => {
    const triggerData = event.data.val();
    const triggerId = event.params.triggerId;

    console.log(`New trigger received: ${triggerId}`, triggerData);

    const model = genAI.getGenerativeModel({ model: "gemini-1.5-flash" });

    const prompt = `
        You are an emergency classification assistant.
        A user has submitted an emergency report:
        - Description: "${triggerData.description || "No description provided"}"
        - Selected type: "${triggerData.type}"
        - Latitude: ${triggerData.latitude}
        - Longitude: ${triggerData.longitude}

        Respond ONLY with a JSON object:
        {
            "crisisType": "<fire|medical|accident|disaster>",
            "confidence": <0.0 to 1.0>,
            "severity": "<low|medium|high|critical>",
            "isAmbiguous": <true|false>,
            "recommendation": "<confirm|monitor|reject>"
        }
    `;

    try {
        const result = await model.generateContent(prompt);
        const responseText = result.response.text();
        
        // Clean and parse JSON
        let cleanedText = responseText.replace(/```json|```/g, "").trim();
        const aiResponse = JSON.parse(cleanedText);

        // Calculate final Confidence Score (Basic version for now)
        // Rule: Base AI score + 10% if description exists
        let finalConfidence = aiResponse.confidence * 100;
        if (triggerData.description) finalConfidence += 10;
        finalConfidence = Math.min(finalConfidence, 100);

        const updates = {
            ai_classification: aiResponse.crisisType,
            ai_confidence: aiResponse.confidence,
            ai_severity: aiResponse.severity,
            is_ambiguous: aiResponse.isAmbiguous,
            recommendation: aiResponse.recommendation,
            final_confidence_score: finalConfidence,
            status: finalConfidence >= 70 ? "confirmed" : (finalConfidence >= 40 ? "suspected" : "rejected"),
            validated_at: admin.database.ServerValue.TIMESTAMP
        };

        await admin.database().ref(`/triggers/${triggerId}`).update(updates);
        console.log(`Trigger ${triggerId} validated successfully with status: ${updates.status}`);

    } catch (error) {
        console.error("Validation Error:", error);
        await admin.database().ref(`/triggers/${triggerId}`).update({
            status: "error",
            error_message: error.message
        });
    }
});
