# FocusFlow — Mobile Anti-Distraction & Ritual Focus Application

FocusFlow is a high-fidelity mobile application designed to enforce intentional screen detachment, lock out distracting apps during scheduled rituals, and sustain long-term habit streaks. The project includes an interactive web prototype faithfully matching all 5 provided screens, paired with an integrated Kotlin Jetpack Compose code inspection suite.

---

## User Review & Critical Decisions

> [!IMPORTANT]
> Based on your answers during Phase 1 clarification, the following architecture decisions are locked:
> - **Platform & Kotlin Integration**: Interactive web application simulating the Android mobile experience, coupled with an interactive Kotlin Jetpack Compose code viewer to inspect and copy production-ready Kotlin Compose files (`@Composable` functions, M3 Theme tokens, and view models).
> - **Initial Screen & Navigation**: The application launches directly on the **Daily Dashboard (Aujourd'hui)**, with seamless navigation across all 5 mockups (Engagement Reminder, Active Session, Ritual Planner, and Simulated Distraction Lock Screen).
> - **Language & Copy**: Pure French (`fr-FR`) throughout the application, identical to the uploaded designs ("Bonjour Thomas", "Séance Musculation & Cardio", "Mode Strict anti-triche", etc.).

---

## 1. Overview & Core Concept

FocusFlow acts as an intentional friction engine for cognitive performance. Rather than acting as a punitive app blocker, it frames sessions as sacred personal commitments ("Rappels d'engagement") for physical workouts, deep work, and mindfulness.

- **Target Persona**: Knowledge workers, students, and athletes who struggle with passive phone scrolling during scheduled routines.
- **Core Value**:
  1. **Strict Session Adherence**: Visual progress rings, live rest timers, and anti-cheat confirmation barriers ("Je reste maître de mes choix") that prevent impulse unlocking.
  2. **Safe Listed Apps**: Controlled access strictly to essential companion tools (e.g., Spotify for workout music, Strava for GPS tracking, Horloge for timers).
  3. **Habit Streak Preservation**: Protective streak counters (7-day flame) with forgiveness mechanics (1 daily emergency joker).
  4. **Native Android Parity**: Authentic Material You (M3) design tokens and idiomatic Kotlin Jetpack Compose code export.

---

## 2. User Experience & Visual Design

### Key User Flows

```
┌────────────────────────────────────────────────────────────────────────┐
│                        1. Aujourd'hui (Dashboard)                      │
│   • Daily goal progress (3h50 / 5h00) • Next session countdown (24:55) │
│   • 5-step daily timeline • FAB "+ Nouvelle activité"                  │
└──────────────────┬─────────────────────────────┬───────────────────────┘
                   │ Tap "Lancer" or             │ Tap "+ Nouvelle activité"
                   │ wait for scheduled time     │ or tap timeline item
                   ▼                             ▼
┌──────────────────────────────────────┐  ┌──────────────────────────────┐
│       2. Rappel d'engagement         │  │ 4. Détails De La Session     │
│  • 17:00 circular clock banner       │  │ • Ritual name, category chips│
│  • 3 Whitelisted apps preview        │  │ • Start time & duration      │
│  • "Démarrer & Bloquer" / "Reporter" │  │ • Strict mode & alarm toggles│
└──────────────────┬───────────────────┘  └──────────────┬───────────────┘
                   │ Tap "Démarrer"                      │ Tap "Enregistrer"
                   ▼                                     ▼
┌──────────────────────────────────────┐  ┌──────────────────────────────┐
│          3. Session Active           │  │ Updates Dashboard Schedule   │
│  • Circular SVG timer (48:19)        │  └──────────────────────────────┘
│  • Chrono séries (90s rest timer)    │
│  • Checklist & charges/reps notes    │
│  • 3-step emergency unlock modal     │
└──────────────────┬───────────────────┘
                   │ User simulates opening Instagram
                   ▼
┌────────────────────────────────────────────────────────────────────────┐
│                 5. Écran Anti-Distraction (Interception)               │
│  • "Instagram est verrouillé" banner • Sandglass pulsing beacon        │
│  • 48:20 countdown • 4-4 breathing mindfulness prompt                  │
│  • "Reprendre mon activité" • 1-minute emergency joker dialog sheet    │
└────────────────────────────────────────────────────────────────────────┘
```

### Visual Identity & Theme Tokens

The application follows the official Material 3 (Material You) palette specified in the design artifacts:

| Token Name | Hex Code | Visual Role |
| :--- | :--- | :--- |
| `surface` | `#F9F9FF` | Soft off-white base canvas |
| `on-surface` | `#111C2D` | Deep slate primary text & high-contrast titles |
| `surface-container-low` | `#F0F3FF` | Level 1 metric & information cards |
| `surface-container` | `#E7EEFF` | Interactive activity rows & schedule items |
| `surface-container-high` | `#DEE8FF` | Button hover surfaces & timer pill backgrounds |
| `surface-container-highest` | `#D8E3FB` | Progress bar background track |
| `primary` | `#0041C5` | Main action buttons, active tab indicators, work tags |
| `primary-container` | `#305CDE` | Deep cobalt hero accent containers |
| `secondary` | `#006C49` | Sport & Health category accent, success rings, pulses |
| `secondary-container` | `#6CF8BB` | Emerald category pill fill & badge backgrounds |
| `tertiary` | `#5D25C7` | Rest & Mindfulness category, timer icons |
| `error` | `#BA1A1A` | Strict lock indicators, unlock warnings, jokers |
| `error-container` | `#FFDAD6` | Emergency warning badge backgrounds |

### Typography & Ergonomics

- **Font Pair**: `Plus Jakarta Sans` for geometric legibility and humanist warmth, with tabular figures (`tabular-nums`) for all countdown clocks, streak metrics, and series timers.
- **Thumb Zone Compliance**: All primary triggers ("Lancer maintenant", "Démarrer & Bloquer", "Reprendre mon activité", "+ Nouvelle activité") sit in the natural thumb zone (bottom 40% of the screen) with a minimum 48px hit box.
- **Safe Area Insets**: Full support for Android edge-to-edge system bars, dynamic top clock status, and bottom navigation pill padding.

---

## 3. Key Product Decisions & Trade-Offs

### 1. Interactive Device Frame vs. Responsive View
- *Chosen Approach*: Responsive mobile simulator container (max-w-md centered on desktop with realistic device top bar and bottom navigation), with a toggle to expand into full-width mode or toggle between the 5 live mockup views directly.
- *Why*: Allows reviewing the exact 390px mobile viewport proportions shown in the user's screenshots, while remaining usable on any display.

### 2. Live Interactive State vs. Static Screens
- *Chosen Approach*: Real interactive state engine across all 5 screens:
  - Timers actually tick down second-by-second with SVG circle offset animations.
  - Pausing/resuming works with toast feedback.
  - Series rest stopwatch (90s) can be started, reset, and closed.
  - Exercise checklist (3/5 validés) allows checking off reps.
  - Anti-distraction modal tests typing the exact phrase "Je reste maître de mes choix" to unlock.
  - Adding a new ritual updates the timeline dynamically and persists in `localStorage`.
- *Why*: Delivers an authentic application experience that can be tested immediately, rather than static mockups.

### 3. Integrated Kotlin Jetpack Compose Code Inspector
- *Chosen Approach*: A slide-out/modal code explorer providing production-ready Kotlin code:
  - `Theme.kt`: Complete Material 3 color scheme and typography definitions.
  - `FocusFlowDashboardScreen.kt`: Jetpack Compose implementation of Screen 1.
  - `EngagementReminderScreen.kt`: Jetpack Compose implementation of Screen 2.
  - `ActiveSessionScreen.kt`: Jetpack Compose implementation of Screen 3.
  - `SessionPlannerScreen.kt`: Jetpack Compose implementation of Screen 4.
  - `DistractionBlockScreen.kt`: Jetpack Compose implementation of Screen 5.
- *Why*: Directly fulfills the user's requirement to "utilise kotlin" by supplying copy-pasteable, clean Android Jetpack Compose code mirroring the exact UI components.

---

## 4. Technical Architecture & Data Strategy

### System Component Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              FocusFlow App                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│  Top Bar (Logo, Notifications, Profile, Kotlin Code Inspector Toggle)       │
├─────────────────────────────────────────────────────────────────────────────┤
│                           Screen Router (Active View)                       │
│  ┌───────────────────┬───────────────────┬───────────────────┐              │
│  │   1. Dashboard    │ 2. Reminder Alert │ 3. Active Session │              │
│  │ (Parcours du jour)│(Rappel d'engagem.)│(Live SVG Timer)   │              │
│  ├───────────────────┼───────────────────┼───────────────────┤              │
│  │4. Ritual Planner  │ 5. Block Screen   │ 6. Kotlin Code    │              │
│  │(Détails session)  │(Instagram Lock)   │    Explorer Sheet │              │
│  └───────────────────┴───────────────────┴───────────────────┘              │
├─────────────────────────────────────────────────────────────────────────────┤
│                          Application State Store                            │
│  • Active Session State (remaining seconds, isPaused, modeStrict)           │
│  • Daily Activities List (5 default timeline rituals + custom items)        │
│  • Whitelisted Apps (Spotify, Strava, Horloge/Minuteur, Urgences)           │
│  • Habit Streak (7 days, emergency joker used: boolean)                     │
│  • Workout Tools (Series timer 90s, checklist exercises, rep weights)      │
├─────────────────────────────────────────────────────────────────────────────┤
│  M3 Bottom Navigation Bar (Aujourd'hui, Semaine, Outils, Stats, Paramètres) │
└─────────────────────────────────────────────────────────────────────────────┘
```

### Component & File Structure

- `src/types/focus.ts`: Data definitions for `Activity`, `FocusCategory`, `WhitelistedApp`, `SessionState`, `WorkoutTool`.
- `src/data/initialData.ts`: Realistic initial state (Thomas's daily schedule, pre-configured whitelisted apps, quotes, streaks).
- `src/components/common/MaterialTopBar.tsx`: M3 header with logo, notification badge, profile icon, and Kotlin code button.
- `src/components/common/MaterialBottomNav.tsx`: 5-tab M3 bottom navigation bar with animated indicator pills.
- `src/components/screens/DashboardScreen.tsx`: Screen 1 (Hero countdown, progress gauge, timeline, coach tip, FAB).
- `src/components/screens/EngagementReminderScreen.tsx`: Screen 2 (Circular 17:00 clock, launch & snooze triggers, dismiss modal).
- `src/components/screens/ActiveSessionScreen.tsx`: Screen 3 (Active SVG countdown, rest timer, checklist, 3-step unlock guard).
- `src/components/screens/SessionPlannerScreen.tsx`: Screen 4 (Ritual name, category selector, time inputs, day recurrence, switches).
- `src/components/screens/DistractionBlockScreen.tsx`: Screen 5 (Instagram interception, sandglass aura, breathing tip, 1-min joker).
- `src/components/kotlin/KotlinCodeViewer.tsx`: Kotlin Compose code viewer with syntax tabs, copy button, and code explanations.
- `src/components/modals/EmergencyUnlockModal.tsx`: Anti-cheat 3-step confirmation modal with anti-relapse copy phrase.
- `src/components/modals/RestTimerModal.tsx`: Interactive 90-second series chrono modal for live workouts.
- `src/components/modals/JokerModal.tsx`: 1-minute emergency pass dialog sheet.
- `src/components/modals/DismissStreakModal.tsx`: 8-day streak loss warning confirmation.
- `src/App.tsx`: Central coordinator managing active screens, navigation state, and real-time timers.

---

## 5. Verification & Validation Steps

1. **Compilation Check**: Run `compile_applet` to confirm strict TypeScript types and zero build errors.
2. **Visual Parity Audit**: Compare each screen view side-by-side with the uploaded reference images (colors, typography, spacing, and icons).
3. **Interactive Test**:
   - Verify countdown timers tick down accurately.
   - Verify "+ Nouvelle activité" opens the planner and saves new activities to the daily timeline.
   - Verify "Lancer maintenant" transitions to the Active Session.
   - Test the "Simuler ouverture Instagram" button to trigger the exact anti-distraction shield.
   - Test the Kotlin code drawer to ensure all Jetpack Compose files are readable and copyable.
