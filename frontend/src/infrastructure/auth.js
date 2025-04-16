import { UserManager } from 'oidc-client-ts';

export class Auth {
    #user = null;

    constructor() {
        this.userManager = new UserManager({
            authority: 'http://localhost:8080/realms/babilonia',
            client_id: 'babilonia-frontend',
            redirect_uri: 'http://localhost:3000/callback',
            response_type: 'code',
            scope: 'openid profile email',
            automaticSilentRenew: true,
            silent_redirect_uri: 'http://localhost:3000/callback'
        });

        this.#setupSilentRenew();
    }

    async loadUser() {
        try {
            this.#user = await this.userManager.getUser();
            if (this.#user && !this.#user.expired) {
                localStorage.setItem('username', this.#user.profile.preferred_username || this.#user.profile.name || '');
                localStorage.setItem('userId', this.#user.profile.sub);
            }
        } catch (err) {
            console.error('Errore nel caricamento dell’utente:', err);
        }
    }

    isAuthenticated() {
        return !!this.#user && !this.#user.expired;
    }

    getToken() {
        return this.#user?.access_token;
    }

    logout() {
        this.userManager.signoutRedirect({ post_logout_redirect_uri: 'http://localhost:3000' });
    }

    async handleCallback() {
        this.#user = await this.userManager.signinRedirectCallback();
        localStorage.setItem('username', this.#user.profile.preferred_username || this.#user.profile.name || '');
        localStorage.setItem('userId', this.#user.profile.sub);
    }

    signinRedirect() {
        return this.userManager.signinRedirect();
    }

    #setupSilentRenew() {
        this.userManager.events.addAccessTokenExpiring(async () => {
            try {
                console.log('Token in scadenza, provo rinnovo...');
                const newUser = await this.userManager.signinSilent();
                this.#user = newUser;
                localStorage.setItem('username', newUser.profile.preferred_username || newUser.profile.name || '');
                localStorage.setItem('userId', newUser.profile.sub);
                console.log('Token rinnovato con successo');
            } catch (err) {
                console.error('Errore nel rinnovo del token:', err);
                this.logout();
            }
        });

        this.userManager.events.addUserLoaded((newUser) => {
            this.#user = newUser;
            console.log('Nuovo token caricato.');
        });

        this.userManager.events.addSilentRenewError((err) => {
            console.error('Errore nel silent renew:', err);
        });
    }
}