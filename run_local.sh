#!/bin/bash
docker-compose -f docker-compose.local.yml -f docker-compose.mock.yml up --remove-orphans