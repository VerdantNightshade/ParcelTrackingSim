# 📦 Project Specification: Courier and Parcel Tracking Simulator

> **Written for:** Group 8 members and the OOP Lab faculty who evaluate the project.

Group 8 received this project in the Object-Oriented Programming Lab (PCC-CS593) at Kalyani Government Engineering College, Department of Computer Science and Engineering. The academic year is 2026-2027 (Even Semester), 5th Semester, 3rd Year (CSE).

## 👥 Group members

| # | Name | Roll number |
| --- | --- | --- |
| 1 | Arunava Roy | 10200124034 |
| 2 | Dwaipayan Datta | 10200124065 |
| 3 | Aayudh Chattarjee | 10200124032 |
| 4 | Nirjhar Debnath | 10200124021 |
| 5 | Agniva Hait | 10200124041 |

## 🎯 Objective

Build a core Java application that tracks parcel shipments through four transit hubs: Dispatch, Sorting Facility, Out-for-Delivery, and Delivered. The application logs each tracking milestone.

## 📝 Problem statement

A logistics system must track the state of each parcel and its transit history. The group builds an object-oriented parcel model that uses:

- Inheritance for shipment speeds: Standard, Express, and Overnight.
- Exception handling for invalid tracking IDs.
- File persistence for tracking events.

## 🧩 Key concepts

| Concept | Use in this project |
| --- | --- |
| Objects, classes, and methods | The classes `Parcel`, `Hub`, and `Milestone`, with methods that update their state. |
| Inheritance and strings | Subclasses for express deliveries. Strings that format the tracking history for CLI output. |
| Exception handling | The program handles invalid tracking IDs. |
| Input and output | The program saves active shipment manifests and status logs in text files. |

## 📋 Constraints

- Use core Java only.
- Do not use a database. Use file handling or in-memory data, through a CLI or a light GUI.
