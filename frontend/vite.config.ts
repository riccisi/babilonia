import { defineConfig } from 'vite';
import tailwindcss from '@tailwindcss/vite';

export default defineConfig({
    plugins: [
        tailwindcss(),
    ],
    server: {
        port: 3000
    },
    build: {
        outDir: '../backend/src/main/resources/static',
        emptyOutDir: true
    }
})