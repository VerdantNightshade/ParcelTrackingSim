#!/bin/bash
# Step 2: compile the test classes against the built program.
# Run test/build.sh first.
# Usage: bash test/compile-tests.sh

source "$(dirname "$0")/env.sh"

if [ ! -d "$CLASSES" ]; then
    echo "The program is not built. Run: bash test/build.sh"
    exit 1
fi
mkdir -p "$TESTCLASSES"

echo "== Step 2: compile the tests"
if ! javac -cp "$(winpath "$CLASSES")" -d "$(winpath "$TESTCLASSES")" "$ROOT"/test/*.java; then
    echo "FAIL compile: javac found errors in test"
    exit 1
fi
echo "PASS compile tests"
