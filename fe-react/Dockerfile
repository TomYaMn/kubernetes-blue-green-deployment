# # Build stage
# FROM node:20-alpine AS builder
# WORKDIR /app
# COPY app/package*.json ./
# RUN npm install
# COPY app .
# RUN npm run build

# # Serve stage
# FROM nginx:alpine
# COPY nginx.conf /etc/nginx/nginx.conf
# COPY --from=builder /app/dist /usr/share/nginx/html
# EXPOSE 80


# Dockerfile for Vite React App

# ---- Stage 1: Build the application ----
    # docker build --build-arg VITE_API_URL="http://your.api.url" -t kub-blue-green-fe-react:latest .
    # docker run -d -p 3000:80 --rm --name my-running-vite-app kub-blue-green-fe-react:latest
    FROM node:20-alpine AS build-stage

    # Set the working directory
    WORKDIR /fe-react
    
    # Copy package.json and lock file (package-lock.json, yarn.lock, or pnpm-lock.yaml)
    # This leverages Docker's layer caching.
    COPY /package*.json ./
    COPY /tsconfig*.json ./
    # If using yarn:
    # COPY yarn.lock ./
    # If using pnpm:
    # COPY pnpm-lock.yaml ./
    
    # Install dependencies
    # Choose the command based on your package manager
    RUN npm install
    # Or: RUN yarn install
    # Or: RUN pnpm install
    
    # Copy the rest of the application source code
    COPY . .
    
    # Environment variable for the API URL (or other Vite env vars)
    # Vite exposes env variables prefixed with VITE_ to the client-side code.
    # This ARG can be set during the `docker build` command.
    ARG VITE_API_URL
    ENV VITE_API_URL=${VITE_API_URL}
    
    # Build the application for production
    # The output is typically in the 'dist' folder for Vite
    RUN npm run build
    # Or: RUN yarn build
    # Or: RUN pnpm build
    
    
    # ---- Stage 2: Serve the application with Nginx ----
    FROM nginx:1.27-alpine AS production-stage
    
    # Set working directory for Nginx
    WORKDIR /usr/share/nginx/html
    
    # Remove default Nginx static assets
    RUN rm -rf ./*
    
    # Copy static assets from the build stage
    COPY --from=build-stage /fe-react/dist .
    
    # Copy custom Nginx configuration
    # This nginx.conf file should be in the same directory as the Dockerfile
    COPY nginx.conf /etc/nginx/conf.d/default.conf
    
    # Expose port 80 (Nginx default)
    EXPOSE 80
    
    # Start Nginx and keep it in the foreground
    CMD ["nginx", "-g", "daemon off;"]
    