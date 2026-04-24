const fetch = require("node-fetch");
const fs = require("fs");

// Read your prompt file
const promptTemplate = fs.readFileSync("ai/prompt.txt", "utf-8");

// Replace placeholders with test data
const prompt = promptTemplate
  .replace("{type}", "fire")
  .replace("{description}", "smoke coming from building")
  .replace("{location_context}", "residential area");

// 🔴 Paste your Gemini API key here
const GEMINI_API_KEY = "YOUR_API_KEY";

async function callGemini() {
  try {
    const response = await fetch(
      `https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=${GEMINI_API_KEY}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          contents: [{ parts: [{ text: prompt }] }]
        })
      }
    );

    const data = await response.json();

    const text = data.candidates[0].content.parts[0].text;

    console.log("Raw Output:\n", text);

    // Try parsing JSON
    const parsed = JSON.parse(text);
    console.log("\nParsed JSON:\n", parsed);

  } catch (error) {
    console.error("Error:", error);
  }
}

callGemini();