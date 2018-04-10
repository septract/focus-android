#!/usr/bin/env bash
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/.

# This script installs google cloud sdk, logs into google firebase, executes tests,
# and collects test artifacts into the test_artifacts folder

# If a command fails then do not proceed and fail this script too.
set -ex

# Both the app apk and the test apk need to be uploaded to nimbledroid for analysis
# Both the webview / geckoview version should be uploaded.  Since they would have different
# package names, they will be displayed as separate apps

# api key should be retrieved from taskcluster

# focusWebviewUniversalDebug and klarGeckoArmDebug should be generated

curl -v -F apk=@'app/build/outputs/apk/focusWebviewUniversal/debug/app-focus-webview-universal-debug.apk' \
 -F test_apk=@'app/build/outputs/apk/androidTest/focusWebviewUniversal/debug/app-focus-webview-universal-debug-androidTest.apk' \
 -F auto_scenarios=false -u <your api key>:

curl -v -F apk=@'app/build/outputs/apk/klarGeckoArm/debug/app-klar-gecko-X86-debug.apk' \
 -F test_apk=@'app/build/outputs/apk/androidTest/klarGeckoArm/debug/app-klar-gecko-X86-debug-androidTest.apk' \
 -F auto_scenarios=false -u <your api key>: