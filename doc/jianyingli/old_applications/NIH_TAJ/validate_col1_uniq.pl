#!/usr/bin/perl 

my $fileIN = $ARGV[0];
my %testHash; 
open (IN, $fileIN) || die "Can't open for read in!\n";
while (<IN>)
{
	chomp;
	@ins = split /\t/, $_;
	if ( exists $testHash {$ins[0]})
	{
		die "Column one contains repeat entry\n";
	}else{
		$testHash {$ins[0]} ++;
	}
}
        
close(IN);
exit(1);



