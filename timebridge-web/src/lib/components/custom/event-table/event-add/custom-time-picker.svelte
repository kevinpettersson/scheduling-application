<script lang="ts">
  import * as InputOTP from "$lib/components/ui/input-otp/index.js";
  import { onMount } from 'svelte';

  let cells = Array(4).fill('');
  let { timeString = $bindable(), ...props } = $props();

  export function saveTime() {
    const hours = cells.slice(0, 2).join('');
    const minutes = cells.slice(2, 4).join('');
    timeString = `${hours}:${minutes}:00Z`;
  }


</script>

<InputOTP.Root maxlength={4}>
  {#snippet children({ cells })}
    <InputOTP.Group>
      {#each cells.slice(0, 2) as cell}
        <InputOTP.Slot {cell}/>
      {/each}
    </InputOTP.Group>
    <span>:</span>
    <InputOTP.Group>
      {#each cells.slice(2, 4) as cell}
        <InputOTP.Slot {cell}/>
      {/each}
    </InputOTP.Group>
  {/snippet}
</InputOTP.Root>