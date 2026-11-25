#!/bin/bash
# Step 1: build test.
# Compiles src/*.java and checks that every class file exists.
# Usage: bash test/build.sh

source "$(dirname "$0")/env.sh"

# Start with an empty output folder
rm -rf "$OUT"
mkdir -p "$CLASSES"

echo "== Step 1: build test"
if ! javac -d "$(winpath "$CLASSES")" "$ROOT"/src/*.java; then
    echo "FAIL build: javac found errors in src"
    exit 1
fi

missing=0
for name in Hub Route Milestone Parcel StandardParcel ExpressParcel OvernightParcel \
            ParcelFactory InvalidTrackingIdException ManifestStore Tracker Main; do
    if [ ! -f "$CLASSES/$name.class" ]; then
        echo "FAIL build: missing $name.class"
        missing=1
    fi
done
if [ $missing -ne 0 ]; then
    exit 1
fi
echo "PASS build (all 12 class files exist)"
