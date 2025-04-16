
export const Routes = {
    HOME: {
        path: '/',
        view: 'dashboard', // resolved da di
    },
    PROJECT_DETAIL: {
        path: '/project/:id',
        factory: (di, data) => di.projectDetail(data.id)
    }
};