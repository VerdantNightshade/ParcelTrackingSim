# 📦 ParcelTrackingSim

A parcel tracking simulator written in Java, with game-like elements. It is the Courier and Parcel Tracking Simulator project of Group 8 for the Object-Oriented Programming Lab (PCC-CS593) at Kalyani Government Engineering College.

## 🎯 What it does

The program tracks parcels through four hubs and logs each step:

```mermaid
flowchart LR
    A[Dispatch] --> B[Sorting Facility] --> C[Out-for-Delivery] --> D[Delivered]
```

- Parcels have one of three speeds: Standard, Express, or Overnight.
- The program rejects invalid tracking IDs with an exception.
- The program saves the shipment manifest and the status log in text files.
- The program prints the tracking history of a parcel in the terminal.

## 🧰 Rules

- Core Java only.
- No database. The program uses text files or in-memory data.

## 👥 Group 8

| Name | Roll number |
| --- | --- |
| Arunava Roy | 10200124034 |
| Dwaipayan Datta | 10200124065 |
| Aayudh Chattarjee | 10200124032 |
| Nirjhar Debnath | 10200124021 |
| Agniva Hait | 10200124041 |

## 📚 Documentation guide

| Doc | Read it for |
| --- | --- |
| [docs/PROJECT-SPEC.md](docs/PROJECT-SPEC.md) | The assignment: objective, problem statement, key concepts, and constraints |
| [docs/BUILD-OVERVIEW.md](docs/BUILD-OVERVIEW.md) | What to build, open questions, and the proposed work split |
| [docs/DESIGN-SKETCH.md](docs/DESIGN-SKETCH.md) | The classes and a sample session |
| [docs/BUILD-GUIDE.md](docs/BUILD-GUIDE.md) | Step-by-step code for every class, and the final test |
| [docs/DESIGN-OPTIONS.md](docs/DESIGN-OPTIONS.md) | The current design, the OOP keywords in use, and three plans to improve it |
