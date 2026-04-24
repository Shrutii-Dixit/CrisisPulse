<div align="center">

# 🚨 CrisisPulse

### AI-Powered Crowd-Driven Emergency Intelligence System

[![Status](https://img.shields.io/badge/Status-Prototype%20Ready-brightgreen?style=for-the-badge)]()
[![Google Solution Challenge](https://img.shields.io/badge/Google%20Solution%20Challenge-2026-4285F4?style=for-the-badge&logo=google&logoColor=white)]()
[![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)]()
[![Backend](https://img.shields.io/badge/Backend-Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)]()
[![AI](https://img.shields.io/badge/AI-Gemini%20API-8E75B2?style=for-the-badge&logo=google&logoColor=white)]()
[![Maps](https://img.shields.io/badge/Maps-Google%20Maps%20API-4285F4?style=for-the-badge&logo=googlemaps&logoColor=white)]()
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)]()

**CrisisPulse** detects crises in real time by transforming citizen-generated signals into validated, actionable insights — acting as a **pre-dispatch intelligence layer** for emergency response.

> *"We don't replace emergency systems — we make them aware faster."*

---

[Overview](#-overview) · [Problem Statement](#-problem-statement) · [Google AI Integration](#-google-ai-integration) · [Core AI Feature](#-core-ai-feature) · [How It Works](#️-how-it-works) · [Architecture](#️-system-architecture) · [Features](#-features) · [Tech Stack](#️-tech-stack) · [Cloud Deployment](#️-cloud-deployment) · [Getting Started](#-getting-started) · [MVP Scope](#-mvp-scope) · [Live Prototype](#-live-prototype) · [Demo Video](#-demo-video) · [API Reference](#-api-reference) · [Contributing](#-contributing) · [License](#-license)

</div>

---

## 📖 Overview

Traditional emergency reporting is **top-down** — incidents are detected only after someone calls in, dispatchers verify, and responders are assigned. This creates dangerous delays.

**CrisisPulse** flips this model. It works **bottom-up**:

```
Citizens → System → Authorities
```

By combining **collective intelligence** with **AI-driven validation powered by Gemini**, CrisisPulse identifies emergencies faster than traditional reporting systems, significantly reducing response delays.

### 🎯 Core Objective

> Reduce emergency response time by detecting crises faster using real-time crowd input and Gemini-powered AI validation.

### What Makes CrisisPulse Different?

| Traditional Systems | CrisisPulse |
|---|---|
| Waits for a single call | Aggregates multiple crowd signals |
| Manual verification by dispatcher | Gemini AI–powered confidence scoring |
| Reactive — responds after report | Proactive — detects as it happens |
| No environmental context | Context-aware anomaly detection |
| Single source of truth | Multi-signal validation engine |

---

## 🔴 Problem Statement

Every minute in an emergency costs lives. Yet today's response systems are built for a slower world.

- ⏱️ **Critical delays** — Emergency detection relies on a single person making a phone call. By the time a dispatcher is notified, precious minutes are already lost.
- 🔀 **Lack of coordination** — Authorities receive fragmented, siloed information with no real-time picture of what is happening on the ground.
- 🧠 **No intelligence layer** — There is no system that continuously listens to crowd signals, validates them automatically, and surfaces a verified crisis before the first official call is even made.

CrisisPulse addresses all three gaps — providing a real-time, AI-validated intelligence layer between citizens and emergency responders.

---

## 🤖 Google AI Integration

CrisisPulse is built on **Google AI** as a first-class, mandatory component of the core pipeline — not an optional add-on.

### Integration: Gemini API via Google AI Studio

The **Gemini API** is called in real time whenever a citizen submits an emergency trigger. It performs three critical functions:

| Function | What Gemini Does |
|---|---|
| **Emergency Classification** | Reads the user's free-text description and classifies the emergency type (`fire`, `medical`, `accident`, `disaster`) with a structured output |
| **Confidence Enhancement** | Provides a semantic confidence score that augments the rule-based engine, catching ambiguous or borderline cases |
| **Ambiguous Trigger Validation** | When the automated system is uncertain (e.g., 40–65% confidence range), Gemini re-evaluates the input to prevent false positives |

### Gemini Integration Flow

```
User Input (text + type + GPS)
        │
        ▼
┌───────────────────────────┐
│     Gemini API Call       │
│  (Google AI Studio Key)   │
│                           │
│  Prompt: classify crisis, │
│  validate intent, score   │
│  severity                 │
└──────────┬────────────────┘
           │
           ▼ Structured JSON Output
┌─────────────────────────────────┐
│  {                              │
│    "crisisType": "fire",        │
│    "confidence": 0.87,          │
│    "severity": "high",          │
│    "isAmbiguous": false,        │
│    "recommendation": "confirm"  │
│  }                              │
└──────────┬──────────────────────┘
           │
           ▼
  Crisis Confidence Engine
  (merges Gemini score + rule-based signals)
           │
           ▼
  Final Alert Decision → Dashboard / Map
```

### Example Gemini Prompt (Cloud Function)

```javascript
const prompt = `
  You are an emergency classification assistant.
  A user has submitted an emergency report with the following details:
  - Description: "${userDescription}"
  - Selected type: "${selectedType}"
  - Location context: "${locationContext}"

  Respond ONLY with a JSON object:
  {
    "crisisType": "<fire|medical|accident|disaster>",
    "confidence": <0.0 to 1.0>,
    "severity": "<low|medium|high|critical>",
    "isAmbiguous": <true|false>,
    "recommendation": "<confirm|monitor|reject>"
  }
`;

const response = await fetch(
  `https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=${GEMINI_API_KEY}`,
  {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      contents: [{ parts: [{ text: prompt }] }]
    })
  }
);
```

### Configuration

Add the following to your environment:

```env
GEMINI_API_KEY=your_google_ai_studio_key_here
```

Obtain your key at: [Google AI Studio](https://aistudio.google.com/app/apikey)

---

## 🧠 Core AI Feature

### Crisis Confidence Engine — Powered by Gemini

The **single most important feature** of CrisisPulse is its AI-driven confidence engine. It answers one question:

> *"Is this actually an emergency?"*

**How it works — in four steps:**

```
Step 1: INPUT
  User submits a trigger (type + optional text + GPS)

Step 2: AI CLASSIFICATION (Gemini API)
  Gemini reads the input and returns a structured classification:
  crisis type, confidence score, severity, and recommendation

Step 3: SCORING
  Gemini's AI score is combined with rule-based signals
  (trigger count, location density, user trust, time clustering)
  into a single Crisis Confidence Score (0–100%)

Step 4: OUTPUT
  < 40%  → Ignored (likely false)
  40–69% → "Suspected" — system monitors for more signals
  ≥ 70%  → "Confirmed" — alert pushed to dashboard and map
```

This hybrid approach — **Gemini's language understanding + rule-based spatial logic** — makes the engine significantly more accurate than either method alone.

---

## 🌐 Why Google AI?

CrisisPulse is built on Google AI for three strategic reasons:

| Reason | Detail |
|---|---|
| **Scalability** | Gemini API scales automatically with request volume — no infrastructure management needed, even during mass-emergency surges |
| **Real-time processing** | Gemini's low-latency responses integrate directly into the live trigger pipeline, adding AI insight without slowing down the system |
| **Firebase integration** | Google AI and Firebase are first-party products — authentication, API access, and Cloud Functions work natively together with minimal friction |

---

## ⚙️ How It Works

CrisisPulse processes emergency data through a **6-stage pipeline**:

```
┌──────────────┐    ┌──────────────────┐    ┌─────────────────┐
│  1. USER     │───▶│  2. INTENT       │───▶│  3. GEMINI AI   │
│  TRIGGER     │    │  VERIFICATION    │    │  VALIDATION     │
│              │    │  LAYER           │    │  ENGINE         │
└──────────────┘    └──────────────────┘    └────────┬────────┘
                                                     │
┌──────────────┐    ┌──────────────────┐    ┌────────▼────────┐
│  6. SMART    │◀───│  5. DECISION     │◀───│  4. CONTEXT     │
│  ALERTS      │    │  OUTPUT          │    │  FILTERING      │
└──────────────┘    └──────────────────┘    └─────────────────┘
```

### 1️⃣ User Trigger — Signal Collection Layer

Users press the **Emergency Button** and select the incident type:

| Type | Icon | Example |
|------|------|---------|
| Fire | 🔥 | Building fire, electrical fire |
| Accident | 🚗 | Vehicle collision, road incident |
| Medical | 🚑 | Heart attack, injury, collapse |
| Disaster | 🌊 | Flood, earthquake, storm |

> **Design Principle:** Make it feel *fast*. No one fills forms during a fire. One tap to trigger, metadata captured automatically (GPS, timestamp).

### 2️⃣ Intent Verification Layer (Anti-Spam)

After trigger, the system runs a quick verification step:

- Confirmation popup: *"Is this a real emergency?"*
- Quick safety check: *"Are you safe?"*
- Optional OTP / callback verification

> 🛡️ Prevents spam and false alerts before they enter the validation pipeline.

### 3️⃣ Gemini AI Validation Engine — The Intelligence Layer

This is the **crown jewel** of CrisisPulse.

The system calls the **Gemini API** to classify and validate the incoming trigger, then combines that output with a weighted multi-signal rule engine to produce a **Crisis Confidence Score**:

```
Confidence Score = 
    (w1 × gemini_confidence) +
    (w2 × trigger_count) +
    (w3 × location_density) +
    (w4 × user_trust_score) +
    (w5 × time_clustering)
```

**Signal Components:**

| Signal | Description | Weight Factor |
|--------|-------------|---------------|
| `gemini_confidence` | AI-generated semantic confidence from Gemini API | **Highest** |
| `trigger_count` | Number of reports in the area | High |
| `location_density` | GPS proximity clustering (spatiotemporal) | High |
| `user_trust_score` | User reliability based on history & accuracy | Medium |
| `time_clustering` | Reports within a sliding time window (e.g., 120 sec) | High |

**Trust Scoring:**

- Verified user > New user
- Past accuracy history matters
- Rate limiting prevents bot-like spam behavior
- Pattern detection flags suspicious trigger patterns

### 4️⃣ Context-Based Filtering

**Environment-aware anomaly detection using location semantics.**

The system validates events against environmental logic to improve accuracy:

| Scenario | Context Check | Result |
|----------|--------------|--------|
| Fire in residential area | Residential zone + multiple triggers | ✅ Likely |
| Fire on highway | Open road, no structures | ❌ Unlikely |
| Accident in jungle | No road infrastructure | ❌ Unlikely |
| Flood in low-elevation area | Flood-prone zone + weather data | ✅ Likely |

> Context > Numbers. 2 triggers on a remote highway at night = **HIGH** weight. 2 triggers in a packed stadium = **LOW** weight.

### 5️⃣ Decision Output

When the confidence score crosses the threshold, the system outputs:

```
┌─────────────────────────────────────────────────┐
│  🔥 FIRE DETECTED                               │
│  ─────────────────────────────────               │
│  Confidence Score:  87%                          │
│  Status:           CONFIRMED ✅                  │
│  Severity:         HIGH                          │
│  Location:         Sector 22, Residential Block  │
│  Active Reports:   14                            │
│  Gemini Validation: Confirmed — "fire"           │
└─────────────────────────────────────────────────┘
```

**Status Levels:**

| Status | Threshold | Action |
|--------|-----------|--------|
| ⚠️ Suspected | 40%–69% | Monitor, gather more signals |
| ✅ Confirmed | 70%+ | Trigger full alert pipeline |

### 6️⃣ Smart Alerts System

CrisisPulse **assists and informs** authorities — it does not command or dispatch them.

**Notifications sent to:**

| Recipient | Type | Details |
|-----------|------|---------|
| Nearby Citizens | Push Alert | Crisis type, distance, safety instructions |
| Fire Department | Dashboard Alert | Location, severity, confidence score |
| Hospitals | Dashboard Alert | Recommended nearest facility, estimated need |
| Police | Dashboard Alert | Incident type, crowd density data |

**Emergency Assistance Recommendations:**

- 🏥 Nearest hospital with route suggestions
- 🚑 Recommended emergency unit types (for authority review)
- 🗺️ Suggested evacuation / approach routes
- 👥 Available volunteers in vicinity

---

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        CLIENT LAYER                             │
│  ┌─────────────┐  ┌──────────────┐  ┌────────────────────────┐ │
│  │  Emergency   │  │  Live Map    │  │  Alert / Dashboard     │ │
│  │  Trigger UI  │  │  View        │  │  Screen                │ │
│  └──────┬──────┘  └──────┬───────┘  └───────────┬────────────┘ │
└─────────┼────────────────┼──────────────────────┼──────────────┘
          │                │                      │
          ▼                ▼                      ▼
┌─────────────────────────────────────────────────────────────────┐
│                      API / BACKEND LAYER                        │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │  Firebase Realtime DB / Cloud Functions                  │   │
│  │  ┌────────────┐ ┌──────────────┐ ┌────────────────────┐ │   │
│  │  │ Auth       │ │ Realtime DB  │ │ Cloud Functions    │ │   │
│  │  │ Service    │ │ (Triggers)   │ │ (Validation Logic) │ │   │
│  │  └────────────┘ └──────────────┘ └────────────────────┘ │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
          │                │                      │
          ▼                ▼                      ▼
┌─────────────────────────────────────────────────────────────────┐
│                    INTELLIGENCE LAYER                            │
│  ┌──────────────┐ ┌───────────────┐ ┌────────────────────────┐ │
│  │ Confidence   │ │ Context       │ │ Trust Score            │ │
│  │ Score Engine │ │ Filter Engine │ │ Calculator             │ │
│  └──────────────┘ └───────────────┘ └────────────────────────┘ │
│  ┌──────────────┐ ┌───────────────┐                            │
│  │ Spatiotemporal│ │ Gemini API   │                            │
│  │ Clustering   │ │ Classifier   │                            │
│  └──────────────┘ └───────────────┘                            │
└─────────────────────────────────────────────────────────────────┘
          │                │                      │
          ▼                ▼                      ▼
┌─────────────────────────────────────────────────────────────────┐
│                     EXTERNAL SERVICES                           │
│  ┌──────────────┐ ┌───────────────┐ ┌────────────────────────┐ │
│  │ Google Maps  │ │ Gemini API    │ │ Push Notification      │ │
│  │ API          │ │ (Google AI)   │ │ Service (FCM)          │ │
│  └──────────────┘ └───────────────┘ └────────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

---

## ⭐ Features

### Core Features

| # | Feature | Description |
|---|---------|-------------|
| 1 | **Crowd-Based Trigger System** | Real-time user input for fast incident detection |
| 2 | **Gemini AI Validation Engine** | Semantic classification + confidence scoring via Gemini API |
| 3 | **Intent Verification Layer** | Anti-spam double verification to prevent false alerts |
| 4 | **Context-Aware Decision Making** | Environment-aware anomaly detection using location semantics |
| 5 | **Smart Notification System** | Alerts to both authorities and nearby citizens |
| 6 | **Emergency Dashboard** | Live incidents map view with real-time status tracking |

### Dashboard Highlights

- 📊 Live incident feed with real-time updates
- 🗺️ Interactive map with crisis markers
- 📈 Confidence score trends and severity tracking
- 🔄 Status lifecycle: Suspected → Confirmed → Resolved

---

## 🛠️ Tech Stack

### Frontend
| Technology | Purpose |
|-----------|---------|
| Android Studio | Mobile application development |
| Java / Kotlin | Primary programming languages |
| XML Layouts | UI design and layouts |
| Google Maps SDK | Interactive map and location display |

### Backend
| Technology | Purpose |
|-----------|---------|
| Firebase Realtime Database | Real-time data sync for triggers and alerts |
| Firebase Authentication | User registration, login, and trust identity |
| Firebase Cloud Functions | Serverless validation logic + Gemini API calls |
| Firebase Hosting | Live prototype deployment |

### AI / Intelligence
| Technology | Purpose |
|-----------|---------|
| **Gemini API (Google AI Studio)** | **Emergency classification, confidence scoring, ambiguity resolution** |
| Rule-Based Scoring Engine | Crisis confidence score calculation (spatiotemporal) |
| Custom Validation Logic | Context filtering and trust scoring |

### APIs & Services
| Service | Purpose |
|---------|---------|
| Google Maps API | Location tracking, geocoding, route suggestions |
| Firebase Cloud Messaging (FCM) | Push notifications to users and responders |
| Google AI Studio | Gemini API access and key management |

---

## ☁️ Cloud Deployment

CrisisPulse is deployed on **Firebase** — Google's fully managed backend platform — enabling real-time performance without managing servers.

### Deployment Architecture

```
┌─────────────────────────────────────────────────────────┐
│                  Firebase Project                       │
│                                                         │
│  ┌──────────────────┐   ┌──────────────────────────┐   │
│  │  Firebase Hosting │   │  Firebase Realtime DB    │   │
│  │  (Web Dashboard)  │   │  - Live trigger sync     │   │
│  │  Live URL: see    │   │  - Active crisis records │   │
│  │  §Live Prototype  │   │  - Alert state tracking  │   │
│  └──────────────────┘   └──────────────────────────┘   │
│                                                         │
│  ┌──────────────────────────────────────────────────┐   │
│  │  Firebase Cloud Functions                        │   │
│  │  - onTriggerReceived → calls Gemini API          │   │
│  │  - calculateConfidenceScore()                    │   │
│  │  - pushAlertToAuthorities()                      │   │
│  │  - updateCrisisStatus()                          │   │
│  └──────────────────────────────────────────────────┘   │
│                                                         │
│  ┌──────────────────────────────────────────────────┐   │
│  │  Firebase Authentication                         │   │
│  │  - Secure user identity & trust scoring          │   │
│  └──────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────┘
```

### How It Runs Live

1. **Android app** connects to Firebase Realtime Database via the Firebase SDK — no REST polling needed.
2. **Cloud Function** triggers automatically when a new emergency report is written to the database.
3. The function calls the **Gemini API**, computes the confidence score, and writes the result back to the database.
4. All connected clients (dashboard, map view) receive the update **in real time** via Firebase's websocket sync — typically within **< 500 ms**.
5. The **web dashboard** is hosted on Firebase Hosting and is accessible from any browser.

### Deploy Your Own Instance

```bash
# Install Firebase CLI
npm install -g firebase-tools

# Login and initialize
firebase login
firebase init

# Deploy Cloud Functions
firebase deploy --only functions

# Deploy web dashboard
firebase deploy --only hosting
```

---

## 🚀 Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio) (Arctic Fox or later)
- JDK 11+
- A Firebase project ([Firebase Console](https://console.firebase.google.com/))
- Google Maps API Key ([Google Cloud Console](https://console.cloud.google.com/))
- Gemini API Key ([Google AI Studio](https://aistudio.google.com/app/apikey))

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/CrisisPulse.git
   cd CrisisPulse
   ```

2. **Open in Android Studio**
   - File → Open → Select the cloned directory
   - Wait for Gradle sync to complete

3. **Configure Firebase**
   - Create a new project at [Firebase Console](https://console.firebase.google.com/)
   - Download `google-services.json`
   - Place it in the `app/` directory
   - Enable **Realtime Database**, **Authentication**, and **Cloud Functions** in Firebase Console

4. **Add API Keys**

   Create or edit `local.properties`:
   ```properties
   MAPS_API_KEY=your_google_maps_api_key_here
   GEMINI_API_KEY=your_google_ai_studio_key_here
   ```

   Or add to `AndroidManifest.xml`:
   ```xml
   <meta-data
       android:name="com.google.android.geo.API_KEY"
       android:value="YOUR_MAPS_API_KEY" />
   ```

5. **Configure Cloud Functions**

   ```bash
   cd functions/
   npm install
   ```

   Edit `functions/.env`:
   ```env
   GEMINI_API_KEY=your_google_ai_studio_key_here
   FIREBASE_PROJECT_ID=your-project-id
   ```

6. **Deploy Cloud Functions**
   ```bash
   firebase deploy --only functions
   ```

7. **Build & Run the App**
   - Select a device or emulator
   - Click **Run ▶** or use `Shift + F10`

---

## 📡 API Reference

### Emergency Trigger

```http
POST /api/v1/trigger
```

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `type` | `string` | ✅ | Crisis type: `fire`, `accident`, `medical`, `disaster` |
| `latitude` | `float` | ✅ | GPS latitude of the user |
| `longitude` | `float` | ✅ | GPS longitude of the user |
| `userId` | `string` | ✅ | Authenticated user identifier |
| `timestamp` | `long` | ✅ | Unix timestamp of trigger |
| `description` | `string` | ❌ | Optional text — passed to Gemini for classification |

**Response:**
```json
{
  "status": "received",
  "triggerId": "trig_abc123",
  "geminiClassification": "fire",
  "geminiConfidence": 0.87,
  "message": "Emergency signal recorded. AI validation in progress."
}
```

### Get Crisis Status

```http
GET /api/v1/crisis/{crisisId}
```

**Response:**
```json
{
  "crisisId": "crisis_xyz789",
  "type": "fire",
  "confidenceScore": 87,
  "geminiValidated": true,
  "status": "confirmed",
  "severity": "high",
  "location": {
    "latitude": 28.6139,
    "longitude": 77.2090,
    "address": "Sector 22, Residential Block"
  },
  "activeReports": 14,
  "nearestHospital": {
    "name": "City General Hospital",
    "distance": "2.3 km",
    "eta": "6 mins"
  }
}
```

### Get Nearby Alerts

```http
GET /api/v1/alerts?lat={latitude}&lng={longitude}&radius={km}
```

---

## 📂 Project Structure

```
CrisisPulse/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/crisispulse/
│   │   │   │   ├── ui/                  # Activities & Fragments
│   │   │   │   │   ├── TriggerActivity  # Emergency button screen
│   │   │   │   │   ├── MapActivity      # Live crisis map
│   │   │   │   │   ├── DashboardActivity# Admin dashboard
│   │   │   │   │   └── AlertActivity    # Alert notifications
│   │   │   │   ├── model/               # Data models
│   │   │   │   │   ├── Crisis           # Crisis data model
│   │   │   │   │   ├── User             # User & trust score
│   │   │   │   │   └── Alert            # Alert payload
│   │   │   │   ├── engine/              # Intelligence layer
│   │   │   │   │   ├── ConfidenceEngine # Score calculation
│   │   │   │   │   ├── GeminiValidator  # Gemini API integration
│   │   │   │   │   ├── ContextFilter    # Location-aware filtering
│   │   │   │   │   └── ClusterEngine    # Spatiotemporal clustering
│   │   │   │   ├── service/             # Background services
│   │   │   │   │   ├── LocationService  # GPS tracking
│   │   │   │   │   └── AlertService     # Push notification handler
│   │   │   │   └── util/                # Helpers & constants
│   │   │   ├── res/                     # Layouts, drawables, values
│   │   │   └── AndroidManifest.xml
│   │   └── test/                        # Unit tests
│   ├── build.gradle
│   └── google-services.json             # Firebase config (gitignored)
├── functions/                           # Firebase Cloud Functions
│   ├── index.js                         # onTriggerReceived + Gemini call
│   ├── geminiValidator.js               # Gemini API wrapper
│   ├── confidenceEngine.js              # Score calculation logic
│   ├── package.json
│   └── .env.example
├── docs/                                # Documentation
├── .gitignore
├── README.md
└── LICENSE
```

---

## ⚠️ Challenges & Solutions

### 1. False Alerts

| Problem | Solution |
|---------|----------|
| Users may spam the trigger button | Rate limiting — cooldown period between triggers |
| Bots or malicious actors | Pattern detection for bot-like behavior |
| Accidental triggers | Intent Verification Layer — confirmation step |
| Repeat offenders | User trust score degrades with false reports |
| Ambiguous text input | Gemini API re-validates before confirming |

### 2. Low User Density

| Problem | Solution |
|---------|----------|
| Few users in an area = weak data | Switch from % logic → absolute + contextual logic |
| Percentage thresholds fail with small numbers | 2 triggers on a remote highway at night = HIGH weight |
| Insufficient text context | Gemini infers from partial input |

### 3. Network Failure

| Problem | Solution |
|---------|----------|
| Disasters often knock out internet | SMS fallback trigger system (concept) |
| Users can't reach servers | Offline trigger storage — syncs when reconnected |

### 4. Legal Constraints

| Problem | Solution |
|---------|----------|
| Cannot legally control emergency services | System **recommends and provides insights** only |
| Liability for false dispatches | Acts as decision-support, not authority override |
| Government regulations | "System assists authorities with real-time intelligence" |

---

## 🏁 MVP Scope

The prototype demonstrates the core detection pipeline end-to-end:

- [x] 🔴 Emergency trigger button with incident type selection
- [x] ✅ Intent verification confirmation step
- [x] 🤖 Gemini AI validation with classification + confidence scoring
- [x] 🗺️ Live map with crisis markers and alerts
- [x] 📊 Emergency dashboard with real-time status tracking

> The MVP is intentionally focused. It proves the core loop: **trigger → AI validate → map alert → dashboard.** Everything else is future scope.

---

## 🔮 Future Scope

| Phase | Feature | Description |
|-------|---------|-------------|
| **Phase 1** | 🌐 IoT Integration | Fire sensors, CCTV feeds, smart city system data |
| **Phase 2** | 📱 Social Media Analysis | Detect crises from trending topics and posts |
| **Phase 3** | 🤖 AI Disaster Prediction | Recommend preparedness based on historical patterns |
| **Phase 4** | 🏛️ Government Integration | Secure data-sharing link with official emergency services |
| **Phase 5** | 📡 Mesh Network | Peer-to-peer communication without internet |

---

## 🔗 Live Prototype

> 🚧 **Prototype deployment in progress.**

| Resource | Link |
|---|---|
| 🌐 Firebase Hosted Web Dashboard | `https://crisispulse-demo.web.app` *(coming soon)* |
| 📱 Android APK (Demo Build) | *(link to be added)* |
| 🔥 Firebase Project Console | *(restricted — contact team)* |

---

## 🎥 Demo Video

> 🎬 **A 2–3 minute walkthrough of CrisisPulse.**

The demo covers:

1. Submitting a live emergency trigger from the Android app
2. Watching the **Gemini API classify and score** the incoming signal in real time
3. Seeing the crisis appear on the live map with confidence score and severity
4. Viewing the authority dashboard update automatically via Firebase sync

📺 **Watch here:** *(YouTube / Drive link — coming soon)*

---

## 🤝 Contributing

Contributions are welcome! Here's how to get started:

1. **Fork** the repository
2. **Create** a feature branch
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. **Commit** your changes
   ```bash
   git commit -m "feat: add your feature description"
   ```
4. **Push** to your branch
   ```bash
   git push origin feature/your-feature-name
   ```
5. **Open** a Pull Request

### Commit Convention

| Prefix | Usage |
|--------|-------|
| `feat:` | New feature |
| `fix:` | Bug fix |
| `docs:` | Documentation changes |
| `refactor:` | Code refactoring |
| `test:` | Adding or updating tests |
| `chore:` | Maintenance tasks |

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

## 💬 The Pitch

> Every second in an emergency matters. Today's systems wait for a phone call. CrisisPulse doesn't wait.
>
> **CrisisPulse** is a crowd-powered, Gemini AI–validated emergency intelligence system that detects crises the moment they begin — not minutes later. Citizens submit a single tap. Gemini classifies the emergency, scores its confidence, and validates ambiguous signals in real time. Firebase syncs that decision to every authority dashboard and map in under a second.
>
> We don't replace emergency services. We give them eyes — before the first call is ever made.

### 💥 Impact Statement

> CrisisPulse closes the intelligence gap between the moment an emergency begins and the moment authorities know about it. By the time a dispatcher receives a traditional call, CrisisPulse has already detected, validated, and surfaced the crisis — with an AI-generated confidence score and a live map pin.
>
> **Faster detection. Smarter validation. Powered by Google AI.**

---

<div align="center">

**Built with urgency. Designed with intelligence. Powered by Google AI.**

[![Google Solution Challenge 2026](https://img.shields.io/badge/Google%20Solution%20Challenge-2026-4285F4?style=for-the-badge&logo=google&logoColor=white)]()

⭐ Star this repo if CrisisPulse resonated with you.

</div>
