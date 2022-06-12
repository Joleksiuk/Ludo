import axios from 'axios';
import authHeader from './services/auth.header';

const ludoAxios = axios.create({
    baseURL: 'http://localhost:8080/',
    headers: authHeader()
})

export default ludoAxios;