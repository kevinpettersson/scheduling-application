<script lang="ts">
	import * as Form from '$lib/components/ui/form/index.js';
	import { Input } from '$lib/components/ui/input/index.js';
	import { superForm, defaults, setError } from 'sveltekit-superforms';
	import { zod } from 'sveltekit-superforms/adapters';
	import { _iCalSchema, type iCalSchema } from '$lib/schemas';
	import { goto } from '$app/navigation';
	import { calendarStore } from '$lib/stores/session-store';
	import { Upload } from 'lucide-svelte';

	const form = superForm(defaults(zod(_iCalSchema)), {
		SPA: true,
		validators: zod(_iCalSchema),
		async onUpdate({ form }) {
			// Call an external API with form.data, await the result and update form
			if (form.valid) {
				let request = `http://localhost:8080/upload?ical=${form.data.url}`;

				const response = await fetch(request, {
					method: 'GET',
					headers: {
						'Content-Type': 'application/json'
					}
				});

				if (!response.ok) {
					throw new Error(`Failed to upload calendar: ${response.statusText}`);
				} else {
					calendarStore.set(await response.json());
					goto('/editor');
				}
			}
		}
	});

	const { form: formData, enhance } = form;
</script>

<!-- <div class="flex h-screen flex-col items-center justify-center" style="background-color: #CBD5E1;"> -->
	<!-- <h1 class="mb-6 font-bold">TimeBridge</h1> -->
<div 
	class="flex h-screen flex-col items-center justify-center" 
	style="background: linear-gradient(to bottom, #CBD5E1 50%, #FFFFFF 50%);position: relative;">
<!-- Ellipse -->
	<div 
	style="
	position: absolute;
	width: 40rem; /* Width of the ellipse */
	height: 20rem; /* Height of the ellipse */
	background-color: #FFFFFF;
	border-radius: 50%; /* Makes it an ellipse */
	top: calc(50% - 5rem); /* Half of the ellipse height to align vertically */
	z-index: 1; /* Make sure the ellipse stays behind */
	">
	</div>
	<!-- LEFT Pole -->
	<div 
    style="
	position: absolute;
	width: 1rem;
	height: 4rem; /* Same height as the ellipse */
	background-color: #FFFFFF;
	top: calc(50% - 4rem); /* Align vertically with the ellipse */
	left: calc(50% - 18rem); /* Position it to the left of the ellipse */
	z-index: 2; /* Make sure the pole stays in front */
    ">
	</div>
	
	<!-- RIGHT Pole -->
	<div 
	style="
	position: absolute;
	width: 1rem;
	height: 4rem; /* Same height as the ellipse */
	background-color: #FFFFFF;
	top: calc(50% - 4rem); /* Align vertically with the ellipse */
	right: calc(50% - 18rem); /* Position it to the right of the ellipse */
	z-index: 2; /* Make sure the pole stays in front */
    ">
	</div>

	<h1 class="mb-6 font-bold" style="font-size: 4rem; color: black; margin-bottom: 19rem;">TimeBridge</h1>

	<form method="GET" use:enhance>
		<Form.Field {form} name="url">
			<Form.Control>
				{#snippet children({ props })}
					<div
						class="flex flex-row items-center gap-3 rounded-xl border p-2 pl-3 ring-zinc-700 focus-within:ring"
						style="position: relative; z-index: 2; top: -6rem;"
					>
						<Input
							{...props}
							class="focus-visible:ring-none h-8 w-96 rounded-sm border-0  px-1  focus:ring-transparent"
							style="background-color: white; color: black; border: 1px solid #ccc;"
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
