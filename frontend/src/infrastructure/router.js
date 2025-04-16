import Navigo from 'navigo';
import { Routes } from './routes.js';

export function defineRoutes(di) {

    const appEl = document.getElementById('app');
    const router = new Navigo('/', { hash: false });

    const normalize = (path) => path.startsWith('/') ? path : `/${path}`;

    // Autenticazione: solo per le route registrate
    const protectedPaths = Object.values(Routes).map(r => r.path);

    router.hooks({
        before: (done, match) => {
            const matchedPath = normalize(match.route.path);
            if (protectedPaths.includes(matchedPath) && !di.auth.isAuthenticated()) {
                return di.auth.signinRedirect();
            }
            done();
        }
    });

    // Registra dinamicamente le view
    Object.values(Routes).forEach(route => {
        router.on(route.path, ({ data }) => {
            const viewInstance = route.factory
                ? route.factory(di, data)
                : di[route.view];
            viewInstance.mount(appEl);
        });
    });

    // Gestione callback separata (senza hook)
    router.on('/callback', async () => {
        await di.auth.handleCallback();
        await di.auth.loadUser();
        router.navigate('/');
    });

    // Not found
    router.notFound(() => {
        appEl.innerHTML = '<p class="text-center mt-10 text-red-600">Pagina non trovata</p>';
    });

    router.resolve();
}