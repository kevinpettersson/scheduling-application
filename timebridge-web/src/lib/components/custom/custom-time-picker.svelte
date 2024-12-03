<script lang="ts">
    import * as InputOTP from "$lib/components/ui/input-otp/index.js";
  </script>
   
  <InputOTP.Root maxlength={4}>
    {#snippet children({ cells })}
      <InputOTP.Group>
        {#each cells.slice(0, 2) as cell}
          <InputOTP.Slot {cell} />
        {/each}
      </InputOTP.Group>
    <span>:</span>

      <InputOTP.Group>
        {#each cells.slice(2, 4) as cell}
          <InputOTP.Slot {cell} />
        {/each}
      </InputOTP.Group>
    {/snippet}
  </InputOTP.Root>