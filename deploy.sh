#!/usr/bin/env bash
mvn clean deploy -DperformRelease=true -DconnectionUrl=scm:git:https://github.com/ITArray/automotion-maven-plugin.git