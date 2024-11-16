import { z } from 'zod';

export const _iCalSchema = z.object({
    url: z.string().url().startsWith('https://cloud.timeedit.net/').endsWith('.ics')
});

export type iCalSchema = typeof _iCalSchema;