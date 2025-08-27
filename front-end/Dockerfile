# Frontend Dockerfile (same for local & prod -- no longer need to copy over dist)
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .

# Pass env variables at build time (Docker build ARG → Vite)
ARG VITE_API_BASE_URL
ENV VITE_API_BASE_URL=$VITE_API_BASE_URL

RUN npm run build

# Use Nginx to serve the built files
FROM nginx:alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /app/dist /usr/share/nginx/html
COPY public /usr/share/nginx/html/images
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]




# # Step 1: Build the React app using Node.js
# FROM node:18-alpine as build
# WORKDIR /app
# COPY package*.json ./
# RUN npm install
# COPY . .
# ENV NODE_OPTIONS=--max_old_space_size=1024
# RUN npm run build && echo "✅ Build succeeded" || (echo "❌ Build failed" && exit 1)   

# # Step 2: Use Nginx to serve the built files
# FROM nginx:alpine
# COPY nginx.conf /etc/nginx/nginx.conf

# # Copy the build files to Nginx's html directory
# COPY --from=build /app/dist /usr/share/nginx/html
# COPY public /usr/share/nginx/html/images

# # Expose port 80 (default for HTTP)
# EXPOSE 80

# # Start Nginx in the foreground
# CMD ["nginx", "-g", "daemon off;"]
