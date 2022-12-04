$score = 0

Get-Content .\input.txt | ForEach-Object {
Write-Host $_
#if he plays rock an I play paper
    if($_[2] -eq "Y")
    {
    Write-Host "rock"
        $score += 2
        if($_[0] -eq "A")
        {
            $score += 6
        }
        if($_[0] -eq "B")
        {
            $score += 3
        }
        if($_[0] -eq "C")
        {
            
        }
    }
        if($_[2] -eq "X")
    {
        $score += 1
        if($_[0] -eq "A")
        {
            $score += 3
        }
        if($_[0] -eq "B")
        {
            $score += 0
        }
        if($_[0] -eq "C")
        {
            $score += 6
        }
    }
        if($_[2] -eq "Z")
    {
        $score += 3
        if($_[0] -eq "A")
        {
            $score += 0
        }
        if($_[0] -eq "B")
        {
            $score += 6
        }
        if($_[0] -eq "C")
        {
            $score += 3
        }
    }

}
Write-Host $score