#!/bin/sh
set -e

TARGET_DIR="/fe-react"

if [ "$(ls -A $TARGET_DIR)" ]; then
  echo "⚠️  The directory is not empty. Please clear /app or use a new mount point."
  exit 1
fi

echo "🚀 Creating React app in $TARGET_DIR with Vite + React + TypeScript"

npm create vite@latest . -- --template react-ts
npm install

echo "✅ React app created successfully in $TARGET_DIR"
