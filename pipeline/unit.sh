#!/bin/sh
echo "This is unit.sh"

set -ex

npm install && \
chown -R 10000:10000 node_modules && \
npm run test


