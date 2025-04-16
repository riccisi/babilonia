import { api } from './upfetch.js';

export class Api {

    constructor(resourcePath, schema) {
        this.resource = resourcePath;
        this.schema = schema;
    }

    getAll() {
        return api(`/${this.resource}`, {
            schema: this.schema ? z.array(this.schema) : undefined
        });
    }

    getById(id) {
        return api(`/${this.resource}/${id}`, {
            schema: this.schema
        });
    }

    create(payload) {
        return api(`/${this.resource}`, {
            method: 'POST',
            body: payload,
            schema: this.schema
        });
    }

    update(id, payload) {
        return api(`/${this.resource}/${id}`, {
            method: 'PUT',
            body: payload,
            schema: this.schema
        });
    }

    delete(id) {
        return api(`/${this.resource}/${id}`, {
            method: 'DELETE'
        });
    }
}