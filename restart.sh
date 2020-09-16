#!/bin/bash
docker-compose -f docker-compose.local.yml -f docker-compose.wiremock.yml -f docker-compose.live.yml down
docker-compose -f docker-compose.local.yml -f docker-compose.wiremock.yml up --remove-orphans