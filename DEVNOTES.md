# 🛠️ Developer Setup Notes

If you have just pulled this repository, follow these steps to get the environment running.

## 1. Prerequisites
- **Android Studio** (Koala or newer)
- **Node.js** (v20+)
- **Firebase CLI** (`npm install -g firebase-tools`)
- **Google AI Studio Key** (for Gemini API)
- **Google Maps API Key**

## 2. Backend Setup (Cloud Functions)
1. Navigate to the functions folder: `cd functions`
2. Install dependencies: `npm install`
3. Create a `.env` file in the `functions/` directory:
   ```env
   GEMINI_API_KEY=your_actual_key_here
   ```
4. Initialize Firebase: `firebase login` then `firebase init`
5. Deploy functions: `firebase deploy --only functions`

## 3. Android App Setup
1. Open the project in **Android Studio**.
2. **Firebase Config:** Download `google-services.json` from your Firebase Console and place it in the `app/` directory.
3. **Maps Config:** Open `local.properties` (create it if it doesn't exist) and add:
   ```properties
   MAPS_API_KEY=your_google_maps_key_here
   ```
4. Sync Gradle and Run.

## 4. Database Structure
The app expects a **Firebase Realtime Database** with the following structure:
```json
{
  "triggers": {
    "unique_trigger_id": {
      "type": "fire",
      "description": "text",
      "latitude": 0.0,
      "longitude": 0.0,
      "status": "pending",
      "timestamp": 123456789
    }
  }
}
```
## 5. CI/CD Pipeline (GitHub Actions)
The project includes a pre-configured CI/CD pipeline:
- **Android CI:** Automatically builds and lints the app on every PR.
- **Firebase CD:** Automatically deploys Cloud Functions to Firebase when code is merged into `main`.

### Setup GitHub Secrets:
To enable automatic deployment, go to your GitHub Repo **Settings > Secrets and variables > Actions** and add:
1. `FIREBASE_SERVICE_ACCOUNT_KEY`: Generate this in your **Google Cloud Console > IAM & Admin > Service Accounts** (Create a key in JSON format and paste the content here).
