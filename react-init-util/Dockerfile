FROM node:20-slim

# Create app directory
WORKDIR /fe-react

# Install create-react-app globally (optional since we use npx)
RUN npm install -g create-react-app

# Copy script
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]

# docker build -t react-app-generator .
# docker run --rm -v "C:\Users\ECQ956\Desktop\kubernetes-blue-green-deployment\fe-react":/fe-react -w /fe-react react-app-generator