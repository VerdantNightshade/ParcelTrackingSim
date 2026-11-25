#!/bin/bash
# Step 3: run every unit test class.
# Run test/build.sh and test/compile-tests.sh first.
# Usage: bash test/unit-tests.sh

source "$(dirname "$0")/env.sh"

if [ ! -d "$TESTCLASSES" ]; then
    echo "The tests are not compiled. Run: bash test/compile-tests.sh"
    exit 1
fi

echo "== Step 3: run the unit tests"
CP="$(winpath "$CLASSES")${SEP}$(winpath "$TESTCLASSES")"
failed=0
total_tests=0
total_checks=0

for name in HubTest RouteTest MilestoneTest ParcelTest ParcelFactoryTest \
            InvalidTrackingIdExceptionTest TrackerTest ManifestStoreTest \
            PersistenceTest MainMenuTest; do
    # Each test class gets its own empty folder. The data files go there.
    WORK="$OUT/work-$name"
    mkdir -p "$WORK"
    output=$(cd "$WORK" && java -cp "$CP" "$name" 2>&1)
    code=$?
    # Show only the failure lines here, the summary line is read below
    echo "$output" | grep -v "^RESULT "

    result=$(echo "$output" | grep "^RESULT ")
    tests=$(echo "$result" | sed -n 's/.* tests=\([0-9]*\).*/\1/p')
    checks=$(echo "$result" | sed -n 's/.* checks=\([0-9]*\).*/\1/p')
    total_tests=$((total_tests + ${tests:-0}))
    total_checks=$((total_checks + ${checks:-0}))

    if [ $code -eq 0 ]; then
        echo "PASS $name (${tests:-0} tests, ${checks:-0} checks)"
    else
        echo "FAIL $name"
        failed=1
    fi
done

echo
echo "Total: $total_tests tests, $total_checks checks"
if [ $failed -ne 0 ]; then
    exit 1
fi
