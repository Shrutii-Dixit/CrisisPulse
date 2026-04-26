# 🔄 CrisisPulse System Workflow

This document explains how data flows through the system from the moment a user taps the emergency button.

## 1. Signal Capture (Android)
- User selects a crisis type (e.g., Fire).
- System captures precise GPS coordinates and a timestamp.
- A "Pending" record is written to `/triggers` in the Firebase Realtime Database.

## 2. Intelligence Trigger (Cloud Functions)
- A Firebase Cloud Function (`onTriggerReceived`) is listening for new entries in `/triggers`.
- When a new trigger appears, the function extracts the data and constructs a prompt for the **Gemini 1.5 Flash** model.

## 3. AI Validation (Gemini API)
- Gemini analyzes the user's description and the selected type.
- It returns a structured JSON including:
    - **Classification:** Does the description match the type?
    - **Confidence Score:** How certain is the AI? (0.0 - 1.0)
    - **Severity:** Is this critical?
- The Cloud Function updates the database record with these insights.

## 4. Confidence Scoring (The Brain)
- The system calculates a `final_confidence_score`.
- **Logic:**
    - `Score >= 70%`: Marked as **Confirmed**.
    - `Score 40-69%`: Marked as **Suspected**.
    - `Score < 40%`: Marked as **Rejected**.

## 5. Real-time Visualization (Android UI)
- **MapActivity:** Only listens for triggers where `status == "confirmed"`. These appear as red markers on the live map.
- **DashboardActivity:** Displays a live feed of ALL triggers (Suspected, Confirmed, Pending) for authorities to review.

## 6. Real-time Sync
Because of Firebase's websocket connection, the update from "Pending" to "Confirmed" usually happens in **under 1 second**, making the map feel instantaneous.
