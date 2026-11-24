#!/bin/bash
# Simple tests for the parcel tracker.
# Usage: bash test/run-tests.sh <folder with the compiled classes>
#
# Each test runs the real program and types the menu choices for it.
# All runs share one temporary folder, so the data files carry over between runs.

CLASSES=$(cd "$1" && pwd)
WORK=$(mktemp -d)
cd "$WORK" || exit 1

failed=0

# run "<typed lines>" runs Main and prints what it shows
run() {
    printf "$1" | java -cp "$CLASSES" Main
}

# check "<test name>" "<output>" "<expected text>"
check() {
    if echo "$2" | grep -qF "$3"; then
        echo "PASS: $1"
    else
        echo "FAIL: $1 (expected to find: $3)"
        failed=1
    fi
}

# Add a parcel of each speed. The IDs count up from ORD1001
out=$(run "1\nExpress\n4\n")
check "add an Express parcel" "$out" "Created parcel ORD1001 (Express) at Dispatch."

out=$(run "1\nStandard\n4\n")
check "add a Standard parcel" "$out" "Created parcel ORD1002 (Standard) at Dispatch."

out=$(run "1\nOvernight\n4\n")
check "add an Overnight parcel" "$out" "Created parcel ORD1003 (Overnight) at Dispatch."

# Move a parcel to the next hub
out=$(run "2\nORD1001\n4\n")
check "advance a parcel" "$out" "ORD1001 moved to Sorting Facility."

# Track a parcel in a new run. This also checks that the files keep the data
out=$(run "3\nORD1001\n4\n")
check "history has the first hub" "$out" "1. Dispatch"
check "history has the second hub" "$out" "2. Sorting Facility"
check "Express delivery days" "$out" "Delivery days: 2"
check "Express fee" "$out" "Total fee: 150.0"

# Each speed has its own fee rule
out=$(run "3\nORD1002\n4\n")
check "Standard fee" "$out" "Total fee: 100.0"

out=$(run "3\nORD1003\n4\n")
check "Overnight fee" "$out" "Total fee: 250.0"

# A parcel stops at the last hub
out=$(run "2\nORD1003\n2\nORD1003\n2\nORD1003\n2\nORD1003\n4\n")
check "parcel reaches Delivered" "$out" "ORD1003 moved to Delivered."
check "parcel cannot go past Delivered" "$out" "ORD1003 is already delivered."

# Wrong input gives a message and does not crash
out=$(run "3\nABC\n4\n")
check "bad ID format" "$out" "Invalid tracking ID: ABC"

out=$(run "3\nORD9999\n4\n")
check "unknown ID" "$out" "Parcel not found: ORD9999"

out=$(run "1\nFast\n4\n")
check "unknown speed" "$out" "Unknown speed: Fast"

out=$(run "9\n4\n")
check "bad menu choice" "$out" "Choose 1, 2, 3, or 4."

out=$(run "4\n")
check "exit" "$out" "Goodbye."

if [ $failed -ne 0 ]; then
    echo "Some tests failed."
    exit 1
fi
echo "All tests passed."
