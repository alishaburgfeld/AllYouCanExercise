# Build stage
# FROM node:18-alpine as build
# WORKDIR /app
# COPY package*.json ./
# RUN npm install
# COPY . .
# RUN npm run build

# Serve with nginx
# FROM nginx:alpine
# COPY --from=build /app/build /usr/share/nginx/html
# COPY nginx.conf /etc/nginx/nginx.conf
# EXPOSE 80


# Step 1: Build the React app using Node.js
FROM node:18-alpine as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
# This generates the static build files in /build
RUN npm run build   
#this will print out the contents of the build folder
RUN ls -l /app

# Step 2: Use Nginx to serve the built files
FROM nginx:alpine

# Remove the default nginx.conf file
RUN rm /etc/nginx/conf.d/default.conf

# Copy custom Nginx config
COPY nginx.conf /etc/nginx/nginx.conf

# Copy the build files to Nginx's html directory
COPY --from=build /app /usr/share/nginx/html

# Expose port 80 (default for HTTP)
EXPOSE 80

# Start Nginx in the foreground
CMD ["nginx", "-g", "daemon off;"]
