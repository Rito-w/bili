#!/usr/bin/env bash
set -euo pipefail

if [[ $# -lt 1 || $# -gt 2 ]]; then
  echo "Usage: $0 <state-name> [output-directory]" >&2
  exit 64
fi

state_name="$1"
output_dir="${2:-work/ui-qa}"
adb_bin="${ADB:-adb}"

mkdir -p "$output_dir"
"$adb_bin" wait-for-device
"$adb_bin" exec-out screencap -p > "$output_dir/$state_name.png"
"$adb_bin" shell uiautomator dump /sdcard/window.xml >/dev/null
"$adb_bin" pull /sdcard/window.xml "$output_dir/$state_name.xml" >/dev/null

echo "Captured $state_name to $output_dir"

