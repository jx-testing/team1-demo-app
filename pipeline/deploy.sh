#!/bin/bash

set -ex

echo "This is deploy.sh"

export REG_IP=$(cat docker-registry-ip-address)

echo "docker-registry-ip-address is $REG_IP"









