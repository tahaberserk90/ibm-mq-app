# Option 1: All uppercase (recommended for clarity)
FROM node:18 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build -- --configuration=production

# Stage 2: Serve with NGINX
FROM nginx:alpine

# Remove default config and prevent daemon duplicate
RUN rm -rf /etc/nginx/conf.d/* && \
    sed -i '/daemon/d' /etc/nginx/nginx.conf

COPY --from=build /app/dist/frontend /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80