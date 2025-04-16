import { z } from 'zod';

export const ProjectSchema = z.object({
    id: z.string(),
    worldName: z.string(),
    systemId: z.string(),
    status: z.enum(['IN_PROGRESS', 'PUBLISHED'])
});