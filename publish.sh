#!/bin/bash

set -e # exit with nonzero exit code if anything fails

openssl aes-256-cbc -K $encrypted_key -iv $encrypted_iv -in secrets.tar.enc -out secrets.tar -d

tar xvf secrets.tar

echo $PGP_PASSPHRASE | gpg --passphrase-fd 0 --batch --yes --import publishing-setup/private.key

export GPG_TTY=/dev/ttys003

cp publishing-setup/credentials.sbt credentials.sbt

export SCALAJS_VERSION="0.6.33"
sbt +publishSigned

export SCALAJS_VERSION="1.4.0"
sbt +publishSigned

sbt sonatypeBundleRelease
