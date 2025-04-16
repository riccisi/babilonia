import { reactive } from '@arrow-js/core'

export class ArrowComponent {

    constructor(initialState = {}) {
        this.state = reactive(initialState);
    }

    render() {
        throw new Error('render() non implementato');
    }

    mount(el) {
        el.innerHTML = '';
        this.render()(el);
    }
}