# NomNow — Product Requirements Document (PRD)

**Version:** 1.0  
**Last Updated:** April 2026  
**Author:** Mahmoud  
**Status:** Active Development  
**Platform:** Android (Jetpack Compose)  
**Market:** Egypt 🇪🇬

---

## Table of Contents

1. [Executive Summary](#1-executive-summary)
2. [Product Vision & Goals](#2-product-vision--goals)
3. [Market Context](#3-market-context)
4. [User Personas](#4-user-personas)
5. [User Journey Maps](#5-user-journey-maps)
6. [Feature Overview](#6-feature-overview)
7. [Functional Requirements — Customer Role](#7-functional-requirements--customer-role)
8. [Functional Requirements — Restaurant Role](#8-functional-requirements--restaurant-role)
9. [Functional Requirements — Driver Role](#9-functional-requirements--driver-role)
10. [Non-Functional Requirements](#10-non-functional-requirements)
11. [Technical Architecture](#11-technical-architecture)
12. [Data Models](#12-data-models)
13. [API Specification](#13-api-specification)
14. [Firebase Architecture](#14-firebase-architecture)
15. [Screen Inventory & UI Specifications](#15-screen-inventory--ui-specifications)
16. [Navigation Architecture](#16-navigation-architecture)
17. [Payment Integration — PayMob](#17-payment-integration--paymob)
18. [Real-Time Tracking Architecture](#18-real-time-tracking-architecture)
19. [Push Notifications — FCM](#19-push-notifications--fcm)
20. [Offline Strategy — Room](#20-offline-strategy--room)
21. [Error Handling Strategy](#21-error-handling-strategy)
22. [Security Requirements](#22-security-requirements)
23. [Sprint Plan & Backlog](#23-sprint-plan--backlog)
24. [Success Metrics & KPIs](#24-success-metrics--kpis)
25. [Risks & Mitigations](#25-risks--mitigations)
26. [Out of Scope](#26-out-of-scope)
27. [Glossary](#27-glossary)

---

## 1. Executive Summary

**NomNow** is a multi-role, offline-first food delivery Android application built for the Egyptian market. It connects three types of users — Customers, Restaurants, and Drivers — on a single platform.

The primary goal of this project is educational: to serve as a portfolio-grade Android application demonstrating mastery of Kotlin, Jetpack Compose, MVVM + Clean Architecture, Firebase, real-time data, payment integration, and Google Maps SDK. The app targets Play Store readiness as a secondary goal and serves as the cornerstone of the developer's CV.

**Key stats at a glance:**

| Property | Value |
|---|---|
| Platform | Android (minSdk 26, targetSdk 35) |
| Language | Kotlin |
| UI Framework | Jetpack Compose |
| Architecture | MVVM + Clean Architecture |
| Backend | MockAPI + Firebase |
| Payment | PayMob (Egyptian gateway) |
| Maps | Google Maps SDK + Places API |
| Duration | 10 Weeks · 5 Sprints |
| Roles | Customer · Restaurant · Driver |
| Currency | Egyptian Pound (EGP) |

---

## 2. Product Vision & Goals

### 2.1 Vision Statement

> *"NomNow is the food delivery app that feels as fast as hunger itself — connecting Egyptian diners to their favorite local restaurants with real-time tracking, seamless payment, and an experience refined enough to stand proudly on any developer's CV."*

### 2.2 Primary Goals

**G1 — Technical Excellence**
Demonstrate a production-level Android codebase using Clean Architecture, dependency injection (Hilt), reactive programming (Kotlin Flow/StateFlow), offline-first patterns (Room), and composable UI design.

**G2 — Full Feature Parity**
Deliver a working, end-to-end food ordering flow: browse → select → cart → payment → real-time tracking → delivery confirmation.

**G3 — Multi-Role System**
A single app binary handles three distinct user roles with separate navigation graphs, dashboards, and permission sets: Customer, Restaurant, Driver.

**G4 — Play Store Readiness**
The final build must meet Play Store quality requirements: proper error states, loading states, accessibility basics (contrast, touch targets), and a polished splash/onboarding experience.

**G5 — CV Impact**
Every technical decision should be justifiable in a job interview. The codebase must be clean, documented, and hosted publicly on GitHub.

### 2.3 Success Definition

The product is considered successful when:
- A customer can browse, order, pay, and track a delivery end-to-end without errors.
- A restaurant owner can receive, accept, and update orders in real time.
- A driver can accept a delivery, update their live location, and mark it delivered.
- The app handles poor connectivity gracefully using cached data.
- The APK is signed, uploaded, and visible on the Play Store (internal testing track minimum).

---

## 3. Market Context

### 3.1 Target Market

- **Geography:** Egypt, initially focused on Tier-1 cities (Cairo, Alexandria, Zagazig, Mansoura).
- **Primary demographic:** Adults 18–35 with smartphones, moderate digital literacy.
- **Competitive landscape:** Talabat (market leader), Elmenus, Otlob. NomNow differentiates through developer-focused transparency and local-first UX.

### 3.2 Localization Requirements

| Requirement | Detail |
|---|---|
| Language | Arabic (primary) + English (secondary) — UI in English for v1 |
| Currency | Egyptian Pound (EGP) |
| Payment | PayMob (dominant Egyptian payment gateway supporting cards, wallets) |
| Time Zone | Africa/Cairo (UTC+2, no DST) |
| Maps | Cairo/Egypt-calibrated Google Maps data |
| Phone Format | +20 country code, 11-digit numbers |

---

## 4. User Personas

### 4.1 Persona 1 — The Hungry Customer

**Name:** Sara, 24  
**Location:** Zagazig, Sharqia  
**Occupation:** University student  
**Device:** Mid-range Android (Samsung Galaxy A-series)  
**Connectivity:** 4G with occasional poor signal

**Goals:**
- Find food quickly without browsing forever.
- Know exactly when her food will arrive.
- Pay with her debit card without leaving the app.
- Reorder her favorites easily.

**Pain Points:**
- Doesn't trust delivery apps with her card data.
- Gets frustrated when the app shows a restaurant as open but it's actually closed.
- Hates not knowing where her food is after placing the order.

**Key Flows Used:** Browse → Search → Restaurant Details → Cart → PayMob → Order Tracking

---

### 4.2 Persona 2 — The Restaurant Manager

**Name:** Ahmed, 38  
**Location:** Maadi, Cairo  
**Occupation:** Owner of a mid-sized koshary restaurant  
**Device:** Budget Android tablet mounted at the counter  
**Connectivity:** Stable WiFi

**Goals:**
- See incoming orders the moment they're placed.
- Accept or reject orders quickly during rush hour.
- Update his menu items and prices without calling anyone.
- See daily order history and earnings.

**Pain Points:**
- Previous apps had a delay in showing new orders — lost revenue.
- No way to mark certain items as temporarily unavailable.
- Too much complexity in the admin interface.

**Key Flows Used:** Incoming Orders → Accept/Reject → Update Status → Menu Management

---

### 4.3 Persona 3 — The Driver

**Name:** Karim, 29  
**Location:** Heliopolis, Cairo  
**Occupation:** Full-time delivery driver  
**Device:** Budget Android (Infinix or Tecno)  
**Connectivity:** Spotty 3G/4G on the road

**Goals:**
- See nearby available orders on a map.
- Navigate to pickup and dropoff efficiently.
- Update his delivery status without many taps.
- Track his daily earnings.

**Pain Points:**
- Navigation apps that quit in the background waste battery.
- Hard to find the restaurant address when descriptions are vague.
- Wants to see earnings per trip before accepting.

**Key Flows Used:** Available Orders Map → Accept Delivery → Live Location Update → Mark Delivered

---

## 5. User Journey Maps

### 5.1 Customer — Place an Order

```
[Opens App]
    ↓
[Sees Splash → Onboarding (first time)]
    ↓
[Sign In / Sign Up]
    ↓
[Home Screen — sees categories + nearby restaurants + banners]
    ↓
[Taps a restaurant → Restaurant Details with menu]
    ↓
[Adds items to cart (+ button → quantity stepper)]
    ↓
[Floating Cart Bar appears: "2 items · 85 EGP → View Cart"]
    ↓
[Cart Screen — reviews items, adjusts quantities, sees total]
    ↓
[Taps "Place Order" → PayMob WebView opens]
    ↓
[Enters card details → Payment success]
    ↓
[Order Confirmation Screen — Order #1042]
    ↓
[Order Status Screen — live stepper updates]
    ↓
[Tracking Map shows driver moving in real time]
    ↓
[Push notification: "Your order has been delivered! 🎉"]
    ↓
[Delivered state → Rate the restaurant CTA]
```

### 5.2 Restaurant — Handle an Incoming Order

```
[Restaurant App Opens]
    ↓
[Firestore listener active in background]
    ↓
[Push notification: "New order received! 🔔"]
    ↓
[Incoming Orders Screen — new order card appears]
    ↓
[Taps order → sees items, customer note, delivery address]
    ↓
[Taps "Accept" → order status → Confirmed]
    ↓
[After preparation → taps "Ready for Pickup"]
    ↓
[Order assigned to available driver]
    ↓
[Order marked "Out for Delivery" automatically]
```

### 5.3 Driver — Complete a Delivery

```
[Driver App Opens → Map View]
    ↓
[Available order pins visible on map]
    ↓
[Taps pin → sees order details: pickup address, dropoff, earnings]
    ↓
[Taps "Accept" → order assigned]
    ↓
[Navigation to restaurant address]
    ↓
[Taps "Picked Up" at restaurant]
    ↓
[Firebase Realtime DB starts broadcasting driver location]
    ↓
[Navigates to customer address]
    ↓
[Taps "Delivered" → order complete]
    ↓
[Earnings updated in profile]
```

---

## 6. Feature Overview

### 6.1 Feature Matrix by Role

| Feature | Customer | Restaurant | Driver |
|---|---|---|---|
| Email/Password Auth | ✅ | ✅ | ✅ |
| Google Sign-In | ✅ | ❌ | ❌ |
| Browse Restaurants | ✅ | ❌ | ❌ |
| Search | ✅ | ❌ | ❌ |
| Restaurant Details + Menu | ✅ | ✅ (own) | ❌ |
| Cart Management | ✅ | ❌ | ❌ |
| Offline Cart (Room) | ✅ | ❌ | ❌ |
| PayMob Payment | ✅ | ❌ | ❌ |
| Order History | ✅ | ✅ | ✅ |
| Real-Time Order Status | ✅ | ✅ | ✅ |
| Push Notifications (FCM) | ✅ | ✅ | ✅ |
| Live Driver Map | ✅ (view) | ❌ | ✅ (broadcast) |
| Menu Management | ❌ | ✅ | ❌ |
| Accept/Reject Orders | ❌ | ✅ | ❌ |
| Available Orders Map | ❌ | ❌ | ✅ |
| Earnings Dashboard | ❌ | ✅ | ✅ |
| Profile + Settings | ✅ | ✅ | ✅ |
| Dark / Light Theme | ✅ | ✅ | ✅ |
| Splash + Onboarding | ✅ | ❌ | ❌ |

---

## 7. Functional Requirements — Customer Role

### 7.1 Authentication

**FR-C-001 — Email/Password Sign Up**
- User provides: Full Name, Email Address, Password (min 8 chars, 1 uppercase, 1 number), Confirm Password.
- System validates all fields in real-time. Error shown inline beneath each field.
- On success: user document created in Firestore `users` collection with role = "customer".
- On failure: specific error message shown (email already exists, weak password, etc.).

**FR-C-002 — Email/Password Sign In**
- User provides Email + Password.
- Firebase Auth validates credentials.
- On success: navigate to Home Screen, store login state in DataStore.
- On failure: show "Incorrect email or password" below the password field.
- "Forgot Password?" link triggers Firebase password reset email.

**FR-C-003 — Google Sign-In**
- One-tap Google Sign-In via Firebase Auth Google provider.
- If new user: create Firestore document automatically.
- If existing user: restore session.
- On cancellation: return to Sign In screen, no error shown.

**FR-C-004 — Session Persistence**
- Login state persisted in Jetpack DataStore.
- App skips Auth flow if valid session exists.
- Session cleared on explicit Sign Out from Profile.

**FR-C-005 — Onboarding**
- Shown only on first launch (tracked via DataStore boolean `onboarding_shown`).
- 3 screens, swipeable, with skip option.
- Navigates to Sign In after "Get Started" or "Skip".

---

### 7.2 Home Screen

**FR-C-010 — Location Display**
- User's current address shown in top bar (city-level, e.g., "Zagazig").
- Tapping opens an address picker modal (Google Places Autocomplete).
- Selected address persisted in DataStore for subsequent sessions.

**FR-C-011 — Search**
- Full-width search bar on Home.
- Tapping navigates to a dedicated Search Screen.
- Search queries restaurants by name, cuisine, and menu items.
- Results update with 300ms debounce as user types.
- Recent searches stored locally in Room (max 10).
- Empty state shown when no results found.

**FR-C-012 — Promo Banners**
- Horizontal auto-scrolling carousel of promotional banners.
- Data sourced from MockAPI `GET /offers`.
- Each banner is an image with gradient overlay and promotional text.
- Auto-scrolls every 4 seconds. Manual swipe overrides auto-scroll.
- Page indicator dots below carousel.

**FR-C-013 — Category Chips**
- Horizontal scrollable row of food category chips.
- Data sourced from MockAPI `GET /categories`.
- Each chip: icon + label (e.g., 🍕 Pizza, 🌮 Egyptian, 🍣 Sushi).
- Tapping a chip filters the restaurant list below.
- "All" chip selected by default.
- Active chip: primary color fill. Inactive: surface with border.

**FR-C-014 — Popular Near You**
- Horizontal scrollable list of restaurant cards.
- Data from MockAPI `GET /restaurants` — sorted by rating descending.
- Each card shows: thumbnail, name, cuisine tags, rating (stars + number), delivery time, delivery fee, open/closed badge.
- Closed restaurants shown with reduced opacity and "Closed" badge.
- Tapping navigates to Restaurant Details.

**FR-C-015 — Special Offers Section**
- Vertical list of full-width offer cards below "Popular Near You".
- Each card: wide image, discount badge ("20% OFF"), name, cuisine, rating.
- Data from MockAPI filtered by `has_offer: true`.

**FR-C-016 — Pull to Refresh**
- Pull-to-refresh gesture refreshes all Home data (banners, categories, restaurants, offers).
- Custom food-themed loading indicator during refresh.

---

### 7.3 Restaurant Details

**FR-C-020 — Hero Display**
- Full-width restaurant hero image (shared element transition from Home card).
- Restaurant name, cuisine type, rating, review count.
- Info row: ⏱ delivery time | 🚚 delivery fee | 🧾 min order.
- Sticky header collapses on scroll — shows compact toolbar with name.

**FR-C-021 — Delivery / Pickup Toggle**
- Segmented control: "Delivery" | "Pickup".
- Delivery: shows delivery fee and estimated time.
- Pickup: hides delivery fee. User must pick up.
- Selection affects checkout flow and order type stored in Firestore.

**FR-C-022 — Menu Categories Tabs**
- Horizontal scrollable tabs for menu categories (e.g., Popular, Burgers, Drinks).
- Tapping a tab smooth-scrolls the menu list to that section.
- Active tab has underline indicator that slides.

**FR-C-023 — Menu Items**
- Each item: food image (right, 80×80dp), name (bold), description (2-line clamp), price.
- "+" button on each item adds to cart.
- Once added, "+" becomes a quantity stepper (−/count/+).
- Items with `available: false` shown grayed out with "Unavailable" label.
- Item tap opens Item Detail bottom sheet with full description + customization options.

**FR-C-024 — Cart Awareness**
- Floating cart summary bar appears at bottom when cart has ≥1 item.
- Shows: item count, total price, "View Cart" → navigates to Cart Screen.
- Disappears when cart is empty.
- Cart is scoped to one restaurant. Adding items from a different restaurant shows a dialog: "Your cart has items from [Restaurant X]. Clear cart and add from [Restaurant Y]?"

---

### 7.4 Cart

**FR-C-030 — Cart Item Management**
- Displays all items in the cart with image, name, customizations, price.
- Quantity stepper on each item (min 1).
- Swipe-to-delete item with red destructive background.
- Increase/decrease quantities updates total in real time.
- Cart state persisted in Room — survives app restarts.

**FR-C-031 — Order Summary**
- Subtotal: sum of all item prices × quantities.
- Delivery fee: fetched from restaurant data.
- Discount: applied if a promo code is used (v1: hardcoded promo code "NOMNOW20" = 20% off).
- Total: subtotal + delivery − discount.
- All values displayed in EGP.

**FR-C-032 — Delivery Address**
- Shows saved address from DataStore.
- "Change" link opens Google Places Autocomplete bottom sheet.
- Selected address saved and shown before checkout.
- Address required before "Place Order" button is enabled.

**FR-C-033 — Place Order**
- "Place Order" button disabled if: cart empty, no delivery address, or address not confirmed.
- On tap: initiates PayMob payment flow.
- During payment loading: button shows spinner, text changes to "Processing…".
- On payment success: order saved to Firestore, cart cleared, navigate to Order Status.
- On payment failure: show error snackbar, remain on Cart Screen.

**FR-C-034 — Empty Cart State**
- Illustrated empty state: bowl + fork icon, "Your cart is empty", "Browse Restaurants" button.
- Navigates back to Home on button tap.

---

### 7.5 Order Tracking

**FR-C-040 — Order Status Stepper**
- Vertical timeline stepper with 5 steps:
  1. Order Placed ✅
  2. Confirmed by Restaurant ✅
  3. Preparing 🟠 (active state has pulsing animation)
  4. Out for Delivery ⚪
  5. Delivered ⚪
- Each step: icon + label + timestamp when completed.
- Steps update in real time via Firestore listener on `orders/{orderId}`.
- Completed steps: primary color icon + solid line below. Active: pulsing dot. Upcoming: muted.

**FR-C-041 — Estimated Time**
- Displayed prominently below header.
- Format: "~25 min" in large primary-colored text.
- Updates when restaurant confirms (restaurant sets preparation time).

**FR-C-042 — Driver Info Card**
- Appears when order status = "out_for_delivery".
- Shows: driver avatar, name, vehicle info, rating, phone icon (opens dialer), chat icon (future).
- Card has subtle border, surface background.

**FR-C-043 — Live Map View**
- "View on Map" button opens full-screen map.
- Driver's location fetched from Firebase Realtime DB `driver_locations/{driverId}`.
- Driver pin moves smoothly as location updates (animated marker movement).
- Customer's delivery address shown as destination pin.
- Route drawn between driver and destination using Google Maps Directions API.
- Map updates location every 5 seconds (battery-aware interval).

**FR-C-044 — Order History**
- Accessible from Profile and Orders tab.
- List of past orders: restaurant name, date, total, status badge.
- Tapping an order shows its full detail (read-only version of Order Status screen).
- "Reorder" button on completed orders re-adds same items to cart.

---

### 7.6 Profile

**FR-C-050 — Profile Screen**
- User avatar (initials fallback if no photo), full name, email.
- Saved addresses list (add/delete).
- Payment methods (v1: card data handled by PayMob, not stored locally).
- Order History shortcut.
- Dark Mode toggle (saved in DataStore).
- "Sign Out" button with confirmation dialog.
- App version displayed at bottom.

---

## 8. Functional Requirements — Restaurant Role

### 8.1 Authentication

**FR-R-001 — Email/Password Only**
- Restaurant accounts created manually (admin seeding in v1 — no self-registration).
- Sign In flow identical to Customer but navigates to Restaurant Dashboard after auth.
- Role stored in Firestore `users/{uid}.role = "restaurant"` and checked on launch.

### 8.2 Incoming Orders Dashboard

**FR-R-010 — Real-Time Order Feed**
- Firestore listener on `orders` collection filtered by `restaurantId == currentRestaurant.id` and `status in ["pending", "confirmed", "preparing"]`.
- New orders appear at the top of the list instantly — no manual refresh needed.
- Each order card: order number, customer name, items summary, total, time received, status badge.
- Unread/new orders have a highlighted left border in primary color with subtle pulsing animation.

**FR-R-011 — Order Detail View**
- Full list of ordered items with quantities.
- Customer delivery address.
- Customer note / special instructions (if provided).
- Estimated preparation time input (text field, in minutes).
- Accept button (green) + Reject button (red with confirmation dialog).

**FR-R-012 — Accept Order**
- Sets `orders/{orderId}.status = "confirmed"`.
- Sets `orders/{orderId}.estimatedTime = restaurantInputMinutes`.
- Triggers FCM notification to customer: "Your order has been confirmed! 🎉".
- Order moves from "New" to "In Progress" section in the dashboard.

**FR-R-013 — Reject Order**
- Confirmation dialog: "Reject this order? The customer will be notified."
- On confirm: sets `orders/{orderId}.status = "rejected"`.
- Triggers FCM notification to customer: "We're sorry, your order was rejected."
- Initiates refund flag in Firestore (v1: manual refund process, PayMob refund API in v2).

**FR-R-014 — Update Order Status**
- "Mark as Ready for Pickup" button appears when status = "confirmed".
- Sets status = "ready_for_pickup".
- FCM notification sent to available drivers (topic-based or individual).
- Once a driver accepts: status auto-updates to "out_for_delivery".

**FR-R-015 — Order History**
- Separate tab: completed and rejected orders.
- Filterable by date range.
- Shows total revenue per day (sum of completed order totals).

### 8.3 Menu Management

**FR-R-020 — View Menu**
- List of all menu items grouped by category.
- Each item: image, name, description, price, available toggle.

**FR-R-021 — Add Menu Item**
- Form: name, description, price (EGP), category (select), image upload (Firebase Storage), available toggle.
- All fields required except description.
- Item saved to Firestore `menus/{restaurantId}/items`.

**FR-R-022 — Edit Menu Item**
- Tap item → same form pre-populated.
- Save updates Firestore document.

**FR-R-023 — Delete Menu Item**
- Long press or swipe → delete with confirmation dialog.
- Soft delete: `available = false` rather than document deletion (preserves order history integrity).

**FR-R-024 — Toggle Item Availability**
- Quick toggle switch on each item row.
- Sets `available = true/false` in Firestore.
- Unavailable items shown grayed out to customers immediately.

### 8.4 Restaurant Profile

**FR-R-030 — Restaurant Info**
- View: restaurant name, address, phone, cuisine type, operating hours.
- Edit: update operating hours and phone number.
- Opening hours affect "Open/Closed" badge visible to customers.

---

## 9. Functional Requirements — Driver Role

### 9.1 Authentication

**FR-D-001 — Email/Password Only**
- Same as Restaurant auth flow.
- Role = "driver" in Firestore. Navigates to Driver Map after sign-in.

### 9.2 Available Orders Map

**FR-D-010 — Map View**
- Full-screen Google Map centered on driver's current GPS location.
- Available order pins shown on map (orders with status = "ready_for_pickup").
- Each pin shows a food bag icon. Tapping shows order summary card (bottom sheet):
  - Pickup restaurant name + address.
  - Dropoff area (city/neighborhood only for privacy).
  - Estimated earnings for this delivery.
  - "Accept" button.

**FR-D-011 — Accept Delivery**
- Sets `orders/{orderId}.driverId = currentDriver.uid`.
- Sets `orders/{orderId}.status = "out_for_delivery"`.
- FCM notification to customer: "Your driver is on the way! 🛵".
- Order removed from available orders map for other drivers.
- Driver navigates to Active Delivery screen.

### 9.3 Active Delivery

**FR-D-020 — Navigation View**
- Google Maps directions: driver location → restaurant (Phase 1 pickup) → customer address (Phase 2 dropoff).
- Route drawn on map. Distance and ETA shown.
- Step-by-step navigation text above the map.

**FR-D-021 — Location Broadcasting**
- While delivery is active, driver location pushed to Firebase Realtime DB every 5 seconds: `driver_locations/{driverId}: {lat, lng, timestamp}`.
- Location updates stop when delivery is marked complete.
- Uses Android FusedLocationProviderClient with priority PRIORITY_HIGH_ACCURACY.
- Background location permission required and requested with rationale.

**FR-D-022 — Status Updates**
- "I've Arrived at Restaurant" button: sets a pickup_arrived flag.
- "Picked Up" button: confirms item pickup, transitions to Phase 2.
- "Delivered" button: sets `orders/{orderId}.status = "delivered"`.
  - Triggers FCM to customer: "Your order has been delivered! 🎉 Enjoy your meal."
  - Updates driver earnings in Firestore.
  - Clears active delivery state.

### 9.4 Driver Earnings

**FR-D-030 — Earnings Screen**
- Today's earnings total (EGP).
- List of completed deliveries today with per-trip earnings.
- Weekly summary: a bar chart of earnings by day.
- All-time total trips completed.

---

## 10. Non-Functional Requirements

### 10.1 Performance

| Metric | Requirement |
|---|---|
| App cold start | < 2 seconds on mid-range device |
| Home screen load (cached) | < 500ms |
| Home screen load (network) | < 3 seconds on 4G |
| Screen transition animation | 60 fps, no jank |
| Map rendering | < 1 second from screen open |
| Payment WebView load | < 4 seconds |
| Firestore listener latency | < 1 second for order updates |

### 10.2 Reliability

- App must not crash on: no internet connection, empty API responses, expired auth tokens, or PayMob timeout.
- Graceful degradation: if MockAPI is unreachable, show cached data from Room with a "Showing offline data" banner.
- Firebase Firestore offline persistence enabled — order status updates queue and sync when connectivity resumes.

### 10.3 Scalability

- MockAPI used for restaurant/menu data in v1. Designed with repository pattern so the data source can be swapped to a real backend without touching ViewModels or Domain layer.
- Firestore data structure designed to avoid N+1 reads (denormalized order documents contain embedded restaurant/item snapshots).

### 10.4 Accessibility

- All interactive elements: minimum touch target 48×48dp.
- Color contrast: minimum 4.5:1 for body text, 3:1 for large text (WCAG AA).
- Content descriptions on all image and icon composables.
- System font scaling supported (sp units used for all text).

### 10.5 Battery & Data Efficiency

- Location updates throttled to 5-second intervals (not continuous streaming).
- Images lazy-loaded with Coil, cached to disk. Max disk cache: 100MB.
- Firestore listeners removed in `onStop()` and re-attached in `onStart()` to avoid background data charges.
- No wakelock usage. App does not prevent the device from sleeping.

### 10.6 Device Support

- **Minimum SDK:** API 26 (Android 8.0 Oreo) — covers ~94% of active Android devices.
- **Target SDK:** API 35 (Android 15).
- **Screen sizes:** Optimized for 5"–6.8" screens. Tablets not in scope for v1.
- **Orientations:** Portrait only (v1). Landscape not supported.

---

## 11. Technical Architecture

### 11.1 Architecture Overview

NomNow follows **Clean Architecture** with a strict separation into three layers:

```
┌─────────────────────────────────────────────┐
│              Presentation Layer              │
│  Jetpack Compose UI + ViewModels (MVVM)      │
│  StateFlow / UiState / UiEvent               │
├─────────────────────────────────────────────┤
│               Domain Layer                   │
│  Use Cases · Repository Interfaces           │
│  Pure Kotlin · Zero Android dependencies     │
├─────────────────────────────────────────────┤
│               Data Layer                     │
│  Remote (Retrofit + MockAPI + Firebase)      │
│  Local (Room Database)                       │
│  Repository Implementations                  │
└─────────────────────────────────────────────┘
```

### 11.2 Project Structure

```
app/
├── core/
│   ├── network/
│   │   ├── RetrofitClient.kt         ← Singleton Retrofit instance
│   │   ├── AuthInterceptor.kt        ← Attaches Firebase ID token to requests
│   │   └── NetworkMonitor.kt         ← Observes connectivity state
│   ├── local/
│   │   ├── AppDatabase.kt            ← Room database definition
│   │   └── converters/               ← TypeConverters for complex types
│   ├── utils/
│   │   ├── Extensions.kt             ← Kotlin extension functions
│   │   ├── Constants.kt              ← API base URLs, keys, timeouts
│   │   └── Result.kt                 ← Sealed class: Success / Error / Loading
│   └── di/
│       ├── NetworkModule.kt          ← Hilt: provides Retrofit, OkHttp
│       ├── DatabaseModule.kt         ← Hilt: provides Room, DAOs
│       ├── FirebaseModule.kt         ← Hilt: provides FirebaseAuth, Firestore
│       └── RepositoryModule.kt       ← Hilt: binds interfaces to implementations
│
├── data/
│   ├── remote/
│   │   ├── api/
│   │   │   ├── RestaurantApi.kt      ← Retrofit interface: restaurants, menus
│   │   │   ├── OrderApi.kt           ← Retrofit interface: orders
│   │   │   └── CategoryApi.kt        ← Retrofit interface: categories, offers
│   │   ├── dto/
│   │   │   ├── RestaurantDto.kt
│   │   │   ├── MenuItemDto.kt
│   │   │   └── OrderDto.kt
│   │   └── mapper/
│   │       └── DtoMappers.kt         ← DTO → Domain model mappers
│   ├── local/
│   │   ├── dao/
│   │   │   ├── CartDao.kt
│   │   │   ├── RestaurantCacheDao.kt
│   │   │   └── SearchHistoryDao.kt
│   │   └── entity/
│   │       ├── CartItemEntity.kt
│   │       ├── RestaurantEntity.kt
│   │       └── SearchHistoryEntity.kt
│   └── repository/
│       ├── RestaurantRepositoryImpl.kt
│       ├── OrderRepositoryImpl.kt
│       ├── CartRepositoryImpl.kt
│       └── AuthRepositoryImpl.kt
│
├── domain/
│   ├── model/
│   │   ├── Restaurant.kt
│   │   ├── MenuItem.kt
│   │   ├── Order.kt
│   │   ├── CartItem.kt
│   │   └── User.kt
│   ├── repository/
│   │   ├── RestaurantRepository.kt   ← Interface
│   │   ├── OrderRepository.kt        ← Interface
│   │   ├── CartRepository.kt         ← Interface
│   │   └── AuthRepository.kt         ← Interface
│   └── usecase/
│       ├── auth/
│       │   ├── SignInUseCase.kt
│       │   ├── SignUpUseCase.kt
│       │   └── SignOutUseCase.kt
│       ├── restaurant/
│       │   ├── GetRestaurantsUseCase.kt
│       │   ├── GetRestaurantDetailsUseCase.kt
│       │   └── SearchRestaurantsUseCase.kt
│       ├── cart/
│       │   ├── AddToCartUseCase.kt
│       │   ├── RemoveFromCartUseCase.kt
│       │   ├── GetCartUseCase.kt
│       │   └── ClearCartUseCase.kt
│       └── order/
│           ├── PlaceOrderUseCase.kt
│           ├── GetOrderStatusUseCase.kt
│           └── GetOrderHistoryUseCase.kt
│
└── presentation/
    ├── auth/
    │   ├── signin/
    │   │   ├── SignInScreen.kt
    │   │   └── SignInViewModel.kt
    │   └── signup/
    │       ├── SignUpScreen.kt
    │       └── SignUpViewModel.kt
    ├── onboarding/
    │   ├── OnboardingScreen.kt
    │   └── SplashScreen.kt
    ├── customer/
    │   ├── home/
    │   ├── search/
    │   ├── restaurant/
    │   ├── cart/
    │   ├── orderstatus/
    │   └── profile/
    ├── restaurant/
    │   ├── dashboard/
    │   ├── orders/
    │   └── menu/
    └── driver/
        ├── map/
        ├── delivery/
        └── earnings/
```

### 11.3 State Management Pattern

Each screen follows a strict **UiState + UiEvent** pattern:

```kotlin
// UiState — describes what the UI should render
data class HomeUiState(
    val isLoading: Boolean = false,
    val restaurants: List<Restaurant> = emptyList(),
    val categories: List<Category> = emptyList(),
    val banners: List<Banner> = emptyList(),
    val error: String? = null
)

// UiEvent — one-time events (navigation, snackbars)
sealed class HomeUiEvent {
    data class NavigateToRestaurant(val restaurantId: String) : HomeUiEvent()
    data class ShowError(val message: String) : HomeUiEvent()
}

// ViewModel
class HomeViewModel @Inject constructor(
    private val getRestaurantsUseCase: GetRestaurantsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<HomeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
}
```

---

## 12. Data Models

### 12.1 User

```kotlin
data class User(
    val uid: String,
    val fullName: String,
    val email: String,
    val phone: String?,
    val role: UserRole,             // CUSTOMER, RESTAURANT, DRIVER
    val profileImageUrl: String?,
    val savedAddresses: List<Address>,
    val createdAt: Long             // epoch millis
)

enum class UserRole { CUSTOMER, RESTAURANT, DRIVER }
```

### 12.2 Restaurant

```kotlin
data class Restaurant(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val cuisineTypes: List<String>,
    val rating: Float,
    val reviewCount: Int,
    val deliveryTimeMinutes: Int,
    val deliveryFeeEgp: Double,
    val minimumOrderEgp: Double,
    val isOpen: Boolean,
    val address: Address,
    val categories: List<MenuCategory>
)
```

### 12.3 MenuItem

```kotlin
data class MenuItem(
    val id: String,
    val restaurantId: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val priceEgp: Double,
    val category: String,
    val isAvailable: Boolean,
    val customizationOptions: List<CustomizationGroup>
)

data class CustomizationGroup(
    val title: String,                    // e.g., "Choose Size"
    val isRequired: Boolean,
    val options: List<CustomizationOption>
)

data class CustomizationOption(
    val label: String,                    // e.g., "Large"
    val additionalPriceEgp: Double
)
```

### 12.4 CartItem

```kotlin
data class CartItem(
    val id: Int = 0,                      // Room auto-generated
    val menuItemId: String,
    val restaurantId: String,
    val name: String,
    val imageUrl: String,
    val basePrice: Double,
    val selectedOptions: List<String>,
    val optionsAdditionalPrice: Double,
    val quantity: Int,
    val note: String?
) {
    val totalPrice: Double
        get() = (basePrice + optionsAdditionalPrice) * quantity
}
```

### 12.5 Order

```kotlin
data class Order(
    val id: String,
    val customerId: String,
    val restaurantId: String,
    val restaurantName: String,
    val driverId: String?,
    val items: List<OrderItem>,
    val deliveryAddress: Address,
    val orderType: OrderType,             // DELIVERY, PICKUP
    val status: OrderStatus,
    val subtotalEgp: Double,
    val deliveryFeeEgp: Double,
    val discountEgp: Double,
    val totalEgp: Double,
    val paymentTransactionId: String?,
    val estimatedTimeMinutes: Int?,
    val customerNote: String?,
    val placedAt: Long,
    val confirmedAt: Long?,
    val deliveredAt: Long?
)

enum class OrderStatus {
    PENDING, CONFIRMED, PREPARING,
    READY_FOR_PICKUP, OUT_FOR_DELIVERY,
    DELIVERED, REJECTED, CANCELLED
}
```

### 12.6 Address

```kotlin
data class Address(
    val label: String?,                   // e.g., "Home", "Work"
    val fullAddress: String,
    val city: String,
    val latitude: Double,
    val longitude: Double
)
```

---

## 13. API Specification

### 13.1 Base Configuration

```
Base URL:     https://[project-id].mockapi.io/api/v1/
Timeout:      30 seconds (connect + read)
Headers:      Content-Type: application/json
```

### 13.2 Endpoints

#### Restaurants

| Method | Endpoint | Description |
|---|---|---|
| GET | `/restaurants` | List all restaurants |
| GET | `/restaurants/{id}` | Get single restaurant details |
| GET | `/restaurants/{id}/menu` | Get full menu for restaurant |

**GET /restaurants — Response:**
```json
[
  {
    "id": "r001",
    "name": "The Burger Joint",
    "description": "Handcrafted burgers made fresh daily.",
    "imageUrl": "https://...",
    "cuisineTypes": ["American", "Burgers"],
    "rating": 4.8,
    "reviewCount": 234,
    "deliveryTimeMinutes": 30,
    "deliveryFeeEgp": 15.0,
    "minimumOrderEgp": 80.0,
    "isOpen": true,
    "address": {
      "fullAddress": "15 Tahrir Square, Cairo",
      "city": "Cairo",
      "latitude": 30.0444,
      "longitude": 31.2357
    }
  }
]
```

#### Categories

| Method | Endpoint | Description |
|---|---|---|
| GET | `/categories` | List all home categories |
| GET | `/offers` | List active promotional banners |

#### Orders

| Method | Endpoint | Description |
|---|---|---|
| POST | `/orders` | Create a new order record |
| GET | `/orders/{userId}` | Get orders for a specific user |
| PATCH | `/orders/{id}` | Update order fields |

**POST /orders — Request Body:**
```json
{
  "customerId": "uid_abc123",
  "restaurantId": "r001",
  "items": [
    {
      "menuItemId": "m001",
      "name": "Classic Cheeseburger",
      "quantity": 2,
      "priceEgp": 65.0,
      "selectedOptions": ["Large Size", "No Pickles"]
    }
  ],
  "deliveryAddress": {
    "fullAddress": "42 Gamal Street, Zagazig",
    "latitude": 30.5877,
    "longitude": 31.5021
  },
  "orderType": "DELIVERY",
  "subtotalEgp": 130.0,
  "deliveryFeeEgp": 15.0,
  "discountEgp": 0.0,
  "totalEgp": 145.0,
  "paymentTransactionId": "paymob_txn_789"
}
```

---

## 14. Firebase Architecture

### 14.1 Firebase Services Used

| Service | Purpose |
|---|---|
| Firebase Auth | User authentication (Email/Password + Google) |
| Firestore | Orders, users, restaurant profiles, menus, reviews |
| Firebase Realtime Database | Live driver location broadcasting |
| Firebase Cloud Messaging (FCM) | Push notifications to all 3 roles |
| Firebase Storage | Restaurant/food images uploaded by restaurant managers |

### 14.2 Firestore Collections

```
firestore/
├── users/
│   └── {uid}/
│       ├── fullName: String
│       ├── email: String
│       ├── role: "customer" | "restaurant" | "driver"
│       ├── profileImageUrl: String?
│       ├── fcmToken: String              ← updated on each login
│       └── savedAddresses: Array
│
├── restaurants/
│   └── {restaurantId}/
│       ├── name: String
│       ├── ownerId: String              ← FK to users/{uid}
│       ├── isOpen: Boolean
│       ├── operatingHours: Map
│       └── menus/                       ← subcollection
│           └── {menuItemId}/
│               ├── name: String
│               ├── priceEgp: Number
│               ├── isAvailable: Boolean
│               └── category: String
│
├── orders/
│   └── {orderId}/
│       ├── customerId: String
│       ├── restaurantId: String
│       ├── driverId: String?
│       ├── status: OrderStatus
│       ├── items: Array<OrderItem>      ← denormalized snapshot
│       ├── totalEgp: Number
│       ├── paymentTransactionId: String
│       ├── deliveryAddress: Map
│       ├── estimatedTimeMinutes: Number?
│       ├── placedAt: Timestamp
│       └── deliveredAt: Timestamp?
│
└── reviews/
    └── {reviewId}/
        ├── orderId: String
        ├── restaurantId: String
        ├── customerId: String
        ├── rating: Number              ← 1–5
        ├── comment: String?
        └── createdAt: Timestamp
```

### 14.3 Realtime Database Structure

```
realtime-db/
└── driver_locations/
    └── {driverId}/
        ├── lat: Double
        ├── lng: Double
        ├── timestamp: Long             ← epoch millis
        └── activeOrderId: String?
```

### 14.4 Firestore Security Rules (Key Rules)

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {

    // Users: read own doc, write own doc
    match /users/{uid} {
      allow read, write: if request.auth.uid == uid;
    }

    // Orders: customer can create, read own orders
    //         restaurant can read/update orders for their restaurant
    //         driver can read/update assigned orders
    match /orders/{orderId} {
      allow create: if request.auth != null
                    && request.resource.data.customerId == request.auth.uid;
      allow read: if request.auth != null
                  && (resource.data.customerId == request.auth.uid
                  || resource.data.restaurantId == getRestaurantId(request.auth.uid)
                  || resource.data.driverId == request.auth.uid);
      allow update: if request.auth != null;
    }

    // Restaurants: public read, owner write only
    match /restaurants/{restaurantId} {
      allow read: if true;
      allow write: if request.auth != null
                   && resource.data.ownerId == request.auth.uid;
    }
  }
}
```

---

## 15. Screen Inventory & UI Specifications

### 15.1 Complete Screen List

| Screen ID | Screen Name | Role | Sprint |
|---|---|---|---|
| SCR-001 | Splash Screen | All | 1 |
| SCR-002 | Onboarding (3 slides) | Customer | 1 |
| SCR-003 | Sign In | All | 1 |
| SCR-004 | Sign Up | Customer | 1 |
| SCR-005 | Home | Customer | 2 |
| SCR-006 | Search | Customer | 2 |
| SCR-007 | Restaurant Details | Customer | 2 |
| SCR-008 | Item Detail (Bottom Sheet) | Customer | 2 |
| SCR-009 | Cart | Customer | 3 |
| SCR-010 | Order Status / Tracking | Customer | 4 |
| SCR-011 | Live Map View | Customer | 5 |
| SCR-012 | Order History | Customer | 3 |
| SCR-013 | Profile | Customer | 3 |
| SCR-014 | Restaurant Dashboard | Restaurant | 4 |
| SCR-015 | Order Detail (Restaurant) | Restaurant | 4 |
| SCR-016 | Menu Management | Restaurant | 4 |
| SCR-017 | Add/Edit Menu Item | Restaurant | 4 |
| SCR-018 | Driver Map (Available Orders) | Driver | 4 |
| SCR-019 | Active Delivery | Driver | 4 |
| SCR-020 | Driver Earnings | Driver | 4 |

### 15.2 Design Tokens

**Color System (see Section 2 — Theme):**
All colors defined as `MaterialTheme.colorScheme` tokens. No hardcoded hex values in composables.

**Spacing Scale:**
`4dp, 8dp, 12dp, 16dp, 20dp, 24dp, 32dp, 48dp, 64dp`

**Corner Radius Scale:**
`4dp (small), 8dp (input), 12dp (card), 16dp (large card), 24dp (bottom sheet), 50% (pill/chip)`

---

## 16. Navigation Architecture

### 16.1 Navigation Graph

NomNow uses **Jetpack Compose Navigation** with a single `NavHost` and nested navigation graphs per role.

```
NavHost (root)
├── auth_graph
│   ├── splash (start)
│   ├── onboarding
│   ├── sign_in
│   └── sign_up
│
├── customer_graph  (entered after auth with role=CUSTOMER)
│   ├── home (start)
│   ├── search
│   ├── restaurant_details/{restaurantId}
│   ├── item_detail/{itemId}    ← BottomSheet navigation
│   ├── cart
│   ├── order_status/{orderId}
│   ├── live_map/{orderId}
│   ├── order_history
│   └── profile
│
├── restaurant_graph  (entered after auth with role=RESTAURANT)
│   ├── dashboard (start)
│   ├── order_detail/{orderId}
│   ├── menu_management
│   └── add_edit_item/{itemId?}
│
└── driver_graph  (entered after auth with role=DRIVER)
    ├── available_orders_map (start)
    ├── active_delivery/{orderId}
    └── earnings
```

### 16.2 Bottom Navigation (Customer)

| Tab | Icon | Destination |
|---|---|---|
| Home | house | home |
| Search | magnifier | search |
| Orders | receipt | order_history |
| Profile | person | profile |

---

## 17. Payment Integration — PayMob

### 17.1 PayMob Flow

PayMob is Egypt's most widely integrated payment gateway. NomNow uses it for card payments (Visa/Mastercard/Meeza).

```
Step 1 — Authentication Token
POST https://accept.paymob.com/api/auth/tokens
Body: { "api_key": "[PAYMOB_API_KEY]" }
Response: { "token": "auth_token_xyz" }

Step 2 — Order Registration
POST https://accept.paymob.com/api/ecommerce/orders
Headers: { "token": "auth_token_xyz" }
Body: {
  "auth_token": "auth_token_xyz",
  "delivery_needed": false,
  "amount_cents": 14500,         ← 145.00 EGP × 100
  "currency": "EGP",
  "items": [...]
}
Response: { "id": "order_id_123" }

Step 3 — Payment Key Request
POST https://accept.paymob.com/api/acceptance/payment_keys
Body: {
  "auth_token": "auth_token_xyz",
  "amount_cents": 14500,
  "expiration": 3600,
  "order_id": "order_id_123",
  "billing_data": { "first_name": "Sara", ... },
  "currency": "EGP",
  "integration_id": [INTEGRATION_ID]
}
Response: { "token": "payment_key_xyz" }

Step 4 — Open WebView
URL: https://accept.paymob.com/api/acceptance/iframes/[IFRAME_ID]?payment_token=payment_key_xyz
Open in Compose WebView composable.

Step 5 — Handle Callback
PayMob redirects to callback URL with transaction result.
App intercepts URL in WebView's shouldOverrideUrlLoading.
Parse: transaction_id, success, amount_cents from query params.

Step 6 — Save to Firestore
On success: save order to Firestore with paymentTransactionId.
On failure: show error snackbar, keep user on Cart Screen.
```

### 17.2 PayMob Security Notes

- API key and integration ID stored in `local.properties` (excluded from git via `.gitignore`).
- In production, Step 1–3 should be done server-side to avoid exposing API key in APK. For v1 (learning/portfolio), client-side is acceptable.
- Never store card numbers or CVV locally. PayMob handles all PCI-DSS compliance.

### 17.3 Test Cards (PayMob Sandbox)

| Card Number | Expiry | CVV | Result |
|---|---|---|---|
| 4987654321098769 | Any future | 123 | Success |
| 4111111111111111 | Any future | 123 | Decline |

---

## 18. Real-Time Tracking Architecture

### 18.1 Location Update Flow

```
Driver Device
    │
    ├── FusedLocationProviderClient
    │       • PRIORITY_HIGH_ACCURACY
    │       • Interval: 5000ms
    │       • FastestInterval: 3000ms
    │
    ▼
Firebase Realtime DB
    └── driver_locations/{driverId}
            { lat, lng, timestamp, activeOrderId }
    │
    ▼
Customer Device (Firestore listener active)
    └── Reads driver_locations/{driverId}
    └── Animates marker on Google Maps
            (smoothly interpolates between positions)
```

### 18.2 Map Marker Animation

```kotlin
// Smooth marker movement between GPS updates
fun animateMarker(marker: Marker, toPosition: LatLng, duration: Long = 1000L) {
    val startPosition = marker.position
    val handler = Handler(Looper.getMainLooper())
    val startTime = SystemClock.uptimeMillis()

    handler.post(object : Runnable {
        override fun run() {
            val elapsed = SystemClock.uptimeMillis() - startTime
            val t = (elapsed.toFloat() / duration).coerceIn(0f, 1f)
            val lat = t * toPosition.latitude + (1 - t) * startPosition.latitude
            val lng = t * toPosition.longitude + (1 - t) * startPosition.longitude
            marker.position = LatLng(lat, lng)
            if (t < 1.0f) handler.postDelayed(this, 16)
        }
    })
}
```

### 18.3 Battery Optimization

- Location updates only active when order status = `out_for_delivery`.
- Updates stop immediately when status changes to `delivered`.
- Uses `removeLocationUpdates()` in `onStop()` lifecycle.
- Location permission rationale shown before requesting.
- Background location permission required only for Driver role.

---

## 19. Push Notifications — FCM

### 19.1 Notification Events

| Event | Trigger | Recipient | Title | Body |
|---|---|---|---|---|
| New Order | Customer places order | Restaurant | "New Order! 🔔" | "Order #[id] just arrived. Tap to review." |
| Order Confirmed | Restaurant accepts | Customer | "Order Confirmed! ✅" | "[Restaurant] confirmed your order. ~[X] min." |
| Order Rejected | Restaurant rejects | Customer | "Order Cancelled 😞" | "Sorry, [Restaurant] couldn't accept your order." |
| Driver Assigned | Driver accepts | Customer | "Driver on the way! 🛵" | "[Driver] is heading to the restaurant now." |
| Out for Delivery | Driver picks up | Customer | "Your food is coming! 🍕" | "[Driver] picked up your order. [X] min away." |
| Delivered | Driver marks delivered | Customer | "Delivered! 🎉" | "Your order from [Restaurant] has arrived. Enjoy!" |
| New Delivery Available | Order ready for pickup | Drivers (topic) | "New Delivery Near You 📦" | "[Restaurant] in [Area] — [X] EGP earnings." |

### 19.2 FCM Token Management

```kotlin
// On every sign-in, refresh and save FCM token to Firestore
FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
    if (task.isSuccessful) {
        val token = task.result
        firestore.collection("users").document(uid)
            .update("fcmToken", token)
    }
}

// Handle token refresh
class NomNowMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        // Update token in Firestore for current signed-in user
    }

    override fun onMessageReceived(message: RemoteMessage) {
        // Build and show notification
        // Handle foreground vs background cases
        // Navigate to correct screen based on message.data["type"]
    }
}
```

### 19.3 Notification Deep Links

| Notification Type | Deep Link Destination |
|---|---|
| New Order (Restaurant) | `nomnow://restaurant/orders/{orderId}` |
| Order Confirmed (Customer) | `nomnow://customer/order/{orderId}` |
| Out for Delivery | `nomnow://customer/tracking/{orderId}` |
| Delivered | `nomnow://customer/order/{orderId}/review` |

---

## 20. Offline Strategy — Room

### 20.1 What is Cached Locally

| Data | Cache Duration | Strategy |
|---|---|---|
| Restaurant list | 30 minutes | Network-first, fallback to cache |
| Menu items | 30 minutes | Network-first, fallback to cache |
| Cart items | Permanent (until order placed) | Local-first (no network) |
| Search history | Permanent (max 10 items) | Local-first |
| User preferences | Permanent | DataStore (not Room) |

### 20.2 Cache Invalidation

```kotlin
// Timestamp-based invalidation in RestaurantRepositoryImpl
suspend fun getRestaurants(): Flow<List<Restaurant>> = flow {
    val cached = restaurantDao.getAll()
    val cacheAge = System.currentTimeMillis() - cached.firstOrNull()?.cachedAt ?: 0

    if (cached.isNotEmpty() && cacheAge < CACHE_DURATION_MS) {
        emit(cached.map { it.toDomain() })
    } else {
        try {
            val fresh = restaurantApi.getRestaurants()
            restaurantDao.clearAndInsert(fresh.map { it.toEntity() })
            emit(fresh.map { it.toDomain() })
        } catch (e: IOException) {
            if (cached.isNotEmpty()) emit(cached.map { it.toDomain() })
            else throw e
        }
    }
}
```

### 20.3 Offline Banner

When the app detects no connectivity (via `NetworkMonitor`), a persistent banner is shown at the top of Home: "📶 No internet connection — showing saved data". Banner disappears automatically when connectivity is restored.

---

## 21. Error Handling Strategy

### 21.1 Error Types & Handling

| Error Type | Handling |
|---|---|
| No internet | Show offline banner + cached data |
| API timeout | Retry once after 3 seconds, then show error snackbar |
| 4xx API error | Show specific message (e.g., "Restaurant not found") |
| 5xx API error | Show generic "Something went wrong. Try again." |
| Firebase Auth error | Show specific message per error code (wrong password, user not found, etc.) |
| PayMob failure | Show error snackbar "Payment failed. Try again." — stay on Cart |
| Location permission denied | Show rationale dialog → if denied twice, show settings deep link |
| FCM token error | Silently retry in background — notifications are non-critical |
| Empty state (no restaurants) | Illustrated empty state with retry button |

### 21.2 Loading States

Every data-loading screen has three visual states:
1. **Loading:** Shimmer skeleton matching the screen layout.
2. **Success:** Data displayed.
3. **Error:** Error message + retry button.

---

## 22. Security Requirements

| Requirement | Implementation |
|---|---|
| API key protection | Stored in `local.properties`, accessed via `BuildConfig`, excluded from git |
| Firebase rules | Firestore security rules enforce per-role data access |
| Auth token refresh | Firebase handles automatically; expired tokens trigger re-login |
| PayMob API key | Never embedded in client APK in production; v1 exception documented |
| No sensitive data in logs | `Timber` with `DebugTree` only in debug builds; no release logging of PII |
| HTTPS only | OkHttp configured with `HttpsOnly` enforcement |
| Certificate pinning | Not required for v1 (MockAPI + Firebase). Add for production backend. |
| Input validation | All user inputs validated client-side before any API call |

---

## 23. Sprint Plan & Backlog

### 23.1 Sprint Overview

| Sprint | Weeks | Focus | Goal |
|---|---|---|---|
| Sprint 1 | 1–2 | Foundation | Setup + Auth + Retrofit + MockAPI + Splash/Onboarding |
| Sprint 2 | 3–4 | Customer Data | Home + Restaurant Details + Cart (Room) |
| Sprint 3 | 5–6 | Customer Flow | Cart Complete + Error Handling + Profile |
| Sprint 4 | 7–8 | Other Roles | FCM + Real-time Tracking + Restaurant Dashboard + Driver |
| Sprint 5 | 9–10 | Polish & Advanced | PayMob + Maps + Unit Tests + Play Store |

### 23.2 Sprint 1 — Foundation (17 + 2 = 19 pts)

| Task | Epic | Points | Priority |
|---|---|---|---|
| Setup Project + Hilt + Navigation | Setup | 3 | 🔴 High |
| Retrofit + OkHttp Setup | Setup | 2 | 🔴 High |
| Room Database Setup + RestaurantDao | Setup | 2 | 🔴 High |
| MockAPI — Create restaurants + menu data | Setup | 2 | 🔴 High |
| Firebase Auth — Email/Password | Auth | 3 | 🔴 High |
| Firebase Auth — Google Sign-in | Auth | 2 | 🔴 High |
| Splash Screen + Onboarding | Auth | 2 | 🟡 Medium |
| DataStore — save login state + role | Setup | 1 | 🔴 High |

### 23.3 Sprint 2 — Customer Data (22 pts)

| Task | Epic | Points | Priority |
|---|---|---|---|
| Home Screen UI (Categories + Featured + Banner) | Home | 5 | 🔴 High |
| Restaurant List — Retrofit Call | Home | 3 | 🔴 High |
| Restaurant Details Screen | Restaurant | 5 | 🔴 High |
| Cart Screen — Items + Quantities | Cart | 5 | 🔴 High |
| Cart CRUD (increase, decrease, delete) | Cart | 4 | 🔴 High |

### 23.4 Sprint 3 — Customer Flow Completion (23 pts)

| Task | Epic | Points | Priority |
|---|---|---|---|
| Cart Complete + Order Summary | Cart | 4 | 🔴 High |
| Error Handling + Loading States (all screens) | Home | 3 | 🔴 High |
| Profile Screen | Auth | 3 | 🟡 Medium |
| Order History Screen | Dashboard | 3 | 🟡 Medium |
| Search Screen + Debounce | Home | 4 | 🟡 Medium |
| Dark Mode toggle (DataStore) | Setup | 2 | 🟢 Low |
| Promo code input ("NOMNOW20") | Cart | 2 | 🟢 Low |
| Pull to refresh (Home) | Home | 2 | 🟡 Medium |

### 23.5 Sprint 4 — Other Roles (30 pts)

| Task | Epic | Points | Priority |
|---|---|---|---|
| Firebase Realtime DB Setup | Tracking | 2 | 🔴 High |
| FCM Setup + Token Registration | Notifications | 3 | 🔴 High |
| FCM — all notification events | Notifications | 3 | 🔴 High |
| Order Status Screen (stepper) | Tracking | 4 | 🔴 High |
| Restaurant: Incoming Orders Screen | Dashboard | 3 | 🔴 High |
| Restaurant: Accept / Reject + Update Status | Dashboard | 2 | 🔴 High |
| Restaurant: Menu Management (Add/Edit/Delete) | Dashboard | 3 | 🟡 Medium |
| Driver: Available Orders Map | Driver | 3 | 🔴 High |
| Driver: Active Delivery Screen | Driver | 3 | 🔴 High |
| Driver: Location Broadcasting | Driver | 4 | 🔴 High |

### 23.6 Sprint 5 — Polish & Advanced (23 pts)

| Task | Epic | Points | Priority |
|---|---|---|---|
| Payment Token Request via Retrofit (PayMob) | Payment | 2 | 🔴 High |
| Save Order in Firestore after Payment | Payment | 2 | 🔴 High |
| PayMob WebView integration + callback handling | Payment | 4 | 🔴 High |
| Google Maps SDK Setup | Maps | 2 | 🔴 High |
| Nearby Restaurants on Map | Maps | 3 | 🟡 Medium |
| Order Tracking Screen + Live Map | Maps | 5 | 🔴 High |
| Unit Tests for UseCases | Setup | 3 | 🟡 Medium |
| Play Store listing (screenshots, description) | Setup | 2 | 🟡 Medium |

---

## 24. Success Metrics & KPIs

### 24.1 Technical KPIs

| Metric | Target |
|---|---|
| Crash-free rate | ≥ 99% on Firebase Crashlytics |
| App startup time (cold) | < 2 seconds |
| Unit test coverage (domain layer) | ≥ 70% |
| ANR rate | < 0.1% |
| Successful order flow end-to-end | 100% in happy path testing |
| Play Store rating (internal track) | N/A (internal only) |

### 24.2 Portfolio KPIs

| Metric | Target |
|---|---|
| GitHub stars (within 3 months) | > 50 |
| README quality score (self-assessed) | Comprehensive setup guide + architecture diagram |
| LinkedIn post reach | > 500 impressions |
| Technical interview references | Used in ≥ 3 job applications |
| Code review pass | Zero critical issues flagged in peer review |

### 24.3 Functional Acceptance Criteria

The app is considered complete when all the following pass manual QA:

- [ ] Customer can sign up, sign in, and sign out successfully.
- [ ] Home screen loads restaurants and categories from MockAPI.
- [ ] Customer can add items from a restaurant to cart (persists after app kill).
- [ ] Customer can complete a PayMob payment in sandbox mode.
- [ ] Order appears in Firestore after payment success.
- [ ] Restaurant receives FCM notification for new order.
- [ ] Restaurant can accept order and update status.
- [ ] Customer sees status updates in real time on Order Status screen.
- [ ] Driver can accept a delivery and broadcast location.
- [ ] Customer sees driver moving on the map in real time.
- [ ] Driver marks order delivered — customer receives FCM notification.
- [ ] App shows cached restaurants when internet is off.
- [ ] Dark mode and light mode both render correctly with full contrast.
- [ ] Splash and onboarding show only on first launch.

---

## 25. Risks & Mitigations

| Risk | Probability | Impact | Mitigation |
|---|---|---|---|
| PayMob account approval delay | Medium | High | Start PayMob registration Week 1. Use sandbox in parallel. |
| Google Maps billing setup | Low | Medium | Enable billing on GCP at project start. Use $200/month free tier. |
| MockAPI rate limiting | Low | Low | Cache aggressively in Room. MockAPI free tier: 100 objects, unlimited requests. |
| Firebase Spark plan limits (free tier) | Medium | Medium | Firestore: 50k reads/day free. Sufficient for dev. Upgrade if needed. |
| Sprint 5 overloading (Maps + PayMob + Tests) | High | Medium | Start PayMob account week 1. Keep Maps basic (no fancy routing). |
| Background location permission rejection | Medium | Medium | Provide clear rationale dialog. Driver flow degrades gracefully without it. |
| Scope creep (adding features mid-sprint) | High | Medium | Freeze scope at Sprint 1 planning. All new ideas go to v2 backlog. |
| Clean Architecture over-engineering | Medium | Low | Follow the structure defined in this PRD. Don't add layers not specified here. |

---

## 26. Out of Scope

The following are explicitly **not** included in v1 of NomNow:

- **Real backend:** No Node.js/Django/Spring server. MockAPI + Firebase only.
- **Admin panel:** No web dashboard for managing restaurants or users.
- **Ratings & Reviews UI:** Data model exists but submission UI deferred to v2.
- **Chat / Messaging:** Between driver, restaurant, and customer.
- **Loyalty points / wallet:** EGP wallet or points system.
- **Multiple currencies:** EGP only.
- **Tablet layout:** Phone portrait only.
- **Arabic language:** UI in English for v1.
- **PayMob refunds:** Manual process for v1. Automated refund API in v2.
- **Restaurant self-registration:** Manual account creation only.
- **Social sharing:** Share restaurant or order to social media.
- **Scheduled orders:** Order for later delivery.
- **Group orders:** Multiple customers contributing to one order.

---

## 27. Glossary

| Term | Definition |
|---|---|
| MockAPI | Free REST API mocking service at mockapi.io. Used as the restaurant data backend. |
| Retrofit | Type-safe HTTP client for Android/Kotlin. Used for all REST API calls. |
| Room | Android persistence library wrapping SQLite. Used for offline caching and cart. |
| Firestore | Firebase's cloud NoSQL document database. Used for orders, users, restaurant profiles. |
| FCM | Firebase Cloud Messaging. Push notification infrastructure. |
| Realtime DB | Firebase's JSON tree database. Used exclusively for live driver location. |
| Hilt | Dependency injection framework for Android, built on Dagger. |
| PayMob | Egyptian payment gateway supporting card payments, Meeza, and mobile wallets. |
| Clean Architecture | Software architecture pattern separating Presentation, Domain, and Data layers. |
| MVVM | Model-View-ViewModel. The UI architecture pattern used in the Presentation layer. |
| StateFlow | Kotlin coroutines state holder. Used to expose UiState from ViewModels. |
| Use Case | A single unit of business logic in the Domain layer. Each use case does one thing. |
| DTO | Data Transfer Object. Raw API response model before mapping to Domain model. |
| EGP | Egyptian Pound — the currency used throughout the app. |
| Deep Link | URI scheme (`nomnow://`) used for FCM notification navigation. |

---

*End of PRD — NomNow v1.0*

*This document should be treated as a living document. Updates require version increment and changelog entry.*
