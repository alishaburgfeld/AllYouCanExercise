#!/usr/bin/env bash
# wait-for-it.sh

# This script waits until the given host and port are available.
# Usage: ./wait-for-it.sh <host>:<port> [timeout] [-- command args]

TIMEOUT=30
QUIET=0

# Parse arguments
while [[ $# -gt 0 ]]
do
  case "$1" in
    *:* ) HOSTPORT=$1; shift ;;
    --timeout ) TIMEOUT=$2; shift 2 ;;
    --quiet ) QUIET=1; shift ;;
    -- ) shift; break ;;
    * ) HOSTPORT=$1; shift ;;
  esac
done

HOST=${HOSTPORT%:*}
PORT=${HOSTPORT#*:}

# Wait for the specified host:port to become available
echo "Waiting for $HOSTPORT..."

for ((i=0; i<$TIMEOUT; i++)); do
  nc -z "$HOST" "$PORT" > /dev/null 2>&1
  if [[ $? -eq 0 ]]; then
    echo "$HOSTPORT is up"
    exit 0
  fi
  sleep 1
done

# If the timeout was reached
if [[ $QUIET -eq 0 ]]; then
  echo "$HOSTPORT is not available after $TIMEOUT seconds"
fi
exit 1
