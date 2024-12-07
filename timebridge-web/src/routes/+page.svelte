<script lang="ts">
	import * as Form from '$lib/components/ui/form/index.js';
	import { Input } from '$lib/components/ui/input/index.js';
	import { superForm, defaults, setError } from 'sveltekit-superforms';
	import { zod } from 'sveltekit-superforms/adapters';
	import { _importSchema, type importSchema } from '$lib/schemas';
	import { goto } from '$app/navigation';
	import { Upload } from 'lucide-svelte';
	import { fetchCalendar } from '$lib/api.svelte';
	import { calendar } from '$lib/store.svelte';

	const form = superForm(defaults(zod(_importSchema)), {
		SPA: true,
		validators: zod(_importSchema),
		onUpdate({ form }) {
			if (form.valid) {
				fetchCalendar(form.data.url);
				goto('/editor');
			}
		}
	});

	const { form: formData, enhance } = form;
</script>

<div class="bg-bridge flex flex-col items-center justify-center color-">
	<h1 class="bowlby-one-regular mb-6 text-5xl font-bold">TimeBridge</h1>
	<form method="GET" use:enhance>
		<Form.Field {form} name="url">
			<Form.Control>
				{#snippet children({ props })}
					<div
						class="flex flex-row items-center gap-3 rounded-xl border bg-white p-2 pl-3 ring-zinc-700 focus-within:ring"
					>
						<Input
							{...props}
							class="focus-visible:ring-none h-8 w-96 rounded-sm border-0  px-1  focus:ring-transparent"
							bind:value={$formData.url}
							placeholder="https://cloud.timeedit.net/chalmers/web/public/ ... .ics"
						/>
						<Form.Button size="icon"><Upload /></Form.Button>
					</div>
				{/snippet}
			</Form.Control>
			<Form.FieldErrors />
		</Form.Field>
	</form>
</div>

<style>
	@import url('https://fonts.googleapis.com/css2?family=Bowlby+One&display=swap');
	.bowlby-one-regular {
		font-family: 'Bowlby One', sans-serif;
		font-weight: 400;
		font-style: normal;
	}
</style>
