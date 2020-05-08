#!/bin/bash

./gradlew publishToMavenLocal
cd demo-project
./gradlew clean check