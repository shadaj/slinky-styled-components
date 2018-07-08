#!/bin/bash

set -e # exit with nonzero exit code if anything fails

openssl aes-256-cbc -K $encrypted_a12df543abe9_key -iv $encrypted_a12df543abe9_iv -in secrets.tar.enc -out secrets.tar -d

tar xvf secrets.tar

sbt publishSigned sonatypeRelease
