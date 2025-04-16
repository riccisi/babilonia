import { html } from '@arrow-js/core';
import { View } from '../infrastructure/view.js';
import { Navbar } from '../components/navbar.js';
import { ProjectList } from '../components/projectlist.js';

export class Dashboard extends View {
    constructor(auth, projects) {
        super();
        this.navbar = new Navbar(auth);
        this.projectList = new ProjectList(projects);
    }

    refresh() {
        this.projectList.load();
    }

    render() {
        return html`
          <div>
            ${this.navbar.render()}
            <main class="p-4">
              ${this.projectList.render()}
            </main>
          </div>
        `;
    }
}
