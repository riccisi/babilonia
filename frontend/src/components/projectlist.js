import { html } from '@arrow-js/core'
import {ArrowComponent} from "../infrastructure/component.js";

export class ProjectList extends ArrowComponent {

    constructor(projects) {
        super({
            projects: [],
            loading: true,
            error: null
        });

        this.projects = projects;
    }

    async load() {
        try {
            const data = await this.projectsApi.getAll();
            this.state.projects = data;
        } catch (err) {
            this.state.error = err.message;
        } finally {
            this.state.loading = false;
        }
    }

    render() {
        return html`
          <div class="p-4">
            <h1 class="text-2xl font-bold mb-4">I tuoi progetti</h1>
    
            ${() => this.state.loading && html`<p>Caricamento...</p>`}
            ${() => this.state.error && html`<p class="text-red-500">${this.state.error}</p>`}
    
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              ${() => this.state.projects.map(project => html`
                <div class="fly-card">
                  <h2 class="text-lg font-semibold">${project.worldName}</h2>
                  <p class="text-sm text-gray-500">Sistema: ${project.systemId}</p>
                  <p class="text-xs text-gray-400">Stato: ${project.status}</p>
                </div>
              `)}
            </div>
          </div>
        `;
    }
}

