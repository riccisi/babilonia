import { up } from 'up-fetch'
import { di } from '../infrastructure/di.js'; // accede a auth

export const api = up(fetch, () => ({
    baseUrl: '/api',
    get headers() {
        return {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${di.auth.getToken()}`
        };
    },
    parseResponse: 'json',
    retry: 1,
    onError: (err, ctx) => {
        console.error(`Errore durante la fetch a ${ctx.url}:`, err);
        throw err;
    }
}));