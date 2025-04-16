import Bottle from 'bottlejs';
import {Auth} from './auth.js';
import {Projects} from '../api/projects.js';
import {Dashboard} from '../views/dashboard.js';

const bottle = new Bottle();

bottle.service('auth', Auth);
bottle.service('projects', Projects);
bottle.service('dashboard', Dashboard, 'auth', 'projects');

export const di = bottle.container;