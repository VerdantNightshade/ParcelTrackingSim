# 🛠️ Build Overview: What We Must Build

> **Written for:** Group 8 members who must agree on the work before coding starts.

This page uses only [PROJECT-SPEC.md](PROJECT-SPEC.md). It is a draft for review.

## 🎯 The goal

We build a Java program that tracks parcels through four hubs and logs each step.

```mermaid
flowchart LR
    A[Dispatch] --> B[Sorting Facility] --> C[Out-for-Delivery] --> D[Delivered]
```

## 🧱 What we must build

| # | Part | What it is | Example |
| --- | --- | --- | --- |
| 1 | `Parcel` class | One shipment with a tracking ID and a state. | `TRK1001` |
| 2 | Speed subclasses | `Standard`, `Express`, and `Overnight` parcels. They inherit from `Parcel`. | `ExpressParcel extends Parcel` |
| 3 | `Hub` class | One transit hub. | Sorting Facility |
| 4 | `Milestone` class | One tracking event. | `TRK1001` reached the Sorting Facility |
| 5 | Invalid ID handling | The program throws and handles an exception for a bad tracking ID. | `Invalid tracking ID: ABC` |
| 6 | File saving | Text files for the shipment manifest and the status log. | `TRK1001,Express,Dispatch` |
| 7 | CLI output | Text that shows the history of one parcel. | `1. Dispatch` |

All examples are made-up samples, not decisions.

## 📏 Rules

- Use core Java only.
- Do not use a database. Use text files or in-memory data.
- Use a command-line interface (CLI) or a light GUI.

## ❓ Questions to settle first

The spec does not answer these questions.

- How do `Standard`, `Express`, and `Overnight` parcels differ?
- What makes a tracking ID invalid?
- What does each line in the manifest and the status log look like?
- What must we hand in besides the code?

## 👥 Proposed work split

Each person owns one part and helps the others on request.

| Member | Roll number | Owns | Parts |
| --- | --- | --- | --- |
| Arunava Roy | 10200124034 | `Parcel` and the speed subclasses | 1, 2 |
| Dwaipayan Datta | 10200124065 | `Hub` and `Milestone` | 3, 4 |
| Aayudh Chattarjee | 10200124032 | Invalid ID exception | 5 |
| Nirjhar Debnath | 10200124021 | Manifest and status log files | 6 |
| Agniva Hait | 10200124041 | CLI and history output | 7 |

See [DESIGN-SKETCH.md](DESIGN-SKETCH.md) for the classes and a sample session.
