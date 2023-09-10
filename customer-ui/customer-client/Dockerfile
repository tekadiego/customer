FROM node:19-alpine
#ARG api_base_url
WORKDIR /app
COPY package*.json .
RUN npm i --silent
COPY . .
#RUN echo "REACT_APP_API_URL=${api_base_url}" > .env
RUN echo "REACT_APP_API_URL=http://localhost:8083" > .env
EXPOSE 3000
CMD ["npm", "start"]