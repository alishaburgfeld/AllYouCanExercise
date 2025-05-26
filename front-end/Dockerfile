# Step 1: Build the React app using Node.js
FROM node:18-alpine as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build && echo "✅ Build succeeded" || (echo "❌ Build failed" && exit 1)   

# Step 2: Use Nginx to serve the built files
FROM nginx:alpine
COPY nginx.conf /etc/nginx/nginx.conf

# Copy the build files to Nginx's html directory
COPY public/images /usr/share/nginx/html/assets
COPY --from=build /app/dist /usr/share/nginx/html

# Expose port 80 (default for HTTP)
EXPOSE 80

# Start Nginx in the foreground
CMD ["nginx", "-g", "daemon off;"]
