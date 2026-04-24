const fetch = (...args) => import('node-fetch').then(({default: fetch}) => fetch(...args));
const fs = require("fs");

// Read your prompt file
const promptTemplate = fs.readFileSync("ai/prompt.txt", "utf-8");

// Replace placeholders with test data
const prompt = promptTemplate
  .replace("{type}", "fire")
  .replace("{description}", "smoke coming from building")
  .replace("{location_context}", "residential area");

// 🔴 Paste your Gemini API key here
const GEMINI_API_KEY = "YOUR_GEMINI_API_KEY";

async function callGemini() {
  try {
    const response = await fetch(
      `https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=${GEMINI_API_KEY}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          contents: [
            {
              parts: [{ text: prompt }]
            }
          ]
        })
      }
    );

    const data = await response.json();

    console.log("FULL RESPONSE:\n", JSON.stringify(data, null, 2));

    if (!data.candidates || !data.candidates[0]) {
      console.log("❌ No valid AI response.");
      return;
    }

    const text = data.candidates[0].content.parts[0].text;

    console.log("Raw Output:\n", text);

    let cleanedText = text;

    // Remove unwanted text before/after JSON
    const jsonStart = cleanedText.indexOf("{");
    const jsonEnd = cleanedText.lastIndexOf("}");

    if (jsonStart !== -1 && jsonEnd !== -1) {
      cleanedText = cleanedText.substring(jsonStart, jsonEnd + 1);
    }

    try {
      const parsed = JSON.parse(cleanedText);
      console.log("\nParsed JSON:\n", parsed);
    } catch (err) {
      console.log("⚠️ JSON parse failed, raw output used instead");
    }

  } catch (error) {
    console.error("Error:", error);
  }
}

callGemini();