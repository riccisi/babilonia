import { ArrowComponent } from './component.js';

export class View extends ArrowComponent {

    constructor(initialState = {}) {
        super(initialState);
    }

    refresh() {
    }

    mount(el) {
        super.mount(el);
        this.refresh();
    }
}