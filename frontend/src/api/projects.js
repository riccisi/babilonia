import {Api} from "./api.js";
import { ProjectSchema } from './schemas.js';

export class Projects extends Api {

    constructor() {
        super('projects', ProjectSchema);
    }
}