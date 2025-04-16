import {html} from "@arrow-js/core";

export class Navbar {

    constructor(auth) {
        this.auth = auth;
        this.username = localStorage.getItem('username') || 'Utente';
    }

    render() {
        return html`
          <nav class="navbar px-6 py-3 flex justify-between items-center">
            <div class="navbar-brand text-xl font-bold">Babilonia</div>
    
            <div class="flex items-center gap-4">
              <span class="text-sm text-gray-300">Ciao, ${this.username}</span>
              <button
                    class="btn btn-danger"
                    @click="${() => this.auth.logout()}"
                  >
                Logout
              </button>
            </div>
          </nav>
        `;
    }
}