<script lang="ts">
	import { Trash2, PenLine } from 'lucide-svelte';

	import * as DropdownMenu from '$lib/components/ui/dropdown-menu/index.js';
	import { buttonVariants } from '$lib/components/ui/button/index.js';
    import { deleteEvent } from '$lib/api.svelte'; 
	import EventModify from './event-modify-dropdown.svelte'

	// input props
	let { event } = $props<{
		event: Event;
	}>();
</script>

<DropdownMenu.Root>
	<DropdownMenu.Trigger class={buttonVariants({ variant: 'outline' })}>...</DropdownMenu.Trigger>
	<DropdownMenu.Content class="w-56">
		<DropdownMenu.Group>
			<DropdownMenu.GroupHeading>Modify Event</DropdownMenu.GroupHeading>
			<DropdownMenu.Separator />
			<DropdownMenu.Group>
				{#if !event.decorators || event.decorators.length === 0}
					<DropdownMenu.Item>
						<PenLine class="mr-2 size-4" />
						<span>
							<EventModify {event} />
						</span>
					</DropdownMenu.Item>
					<DropdownMenu.Separator />
				{/if}
				<DropdownMenu.Item>
                    <Trash2 class="mr-2 size-4" />
                    <span onclick={() => deleteEvent(event)} >Delete</span>
				</DropdownMenu.Item>
			</DropdownMenu.Group>
		</DropdownMenu.Group>
	</DropdownMenu.Content>
</DropdownMenu.Root>
